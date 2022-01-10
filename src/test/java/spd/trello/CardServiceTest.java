package spd.trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.*;
import spd.trello.repository.BoardRepository;
import spd.trello.repository.CardListRepository;
import spd.trello.repository.CardRepository;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardServiceTest extends BaseTest{
    AbstractService<Workspace> workspaceService = new WorkspaceService(new WorkspaceRepository());
    AbstractService<Board> boardService = new BoardService(new BoardRepository());
    AbstractService<CardList> cardListService = new CardListService(new CardListRepository());
    AbstractService<Card> cardService = new CardService(new CardRepository());
    UUID workspace_id;
    UUID board_id;
    UUID cardlist_id;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;


    @BeforeEach
    public void objects_init()
    {
        testWorkspace = new Workspace();
        testWorkspace.setName("Test");
        testWorkspace.setDescription("12354");
        testWorkspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspaceService.create(null, testWorkspace);
        workspace_id = testWorkspace.getId();

        testBoard = new Board();
        testBoard.setName("testBoard");
        testBoard.setArchived(false);
        testBoard.setDescription("12345");
        testBoard.setVisibility(BoardVisibility.WORKSPACE);
        boardService.create(workspace_id,testBoard);
        board_id = testBoard.getId();

        testCardList = new CardList();
        testCardList.setName("firstCardList");
        testCardList.setArchived(false);
        cardListService.create(board_id, testCardList);
        cardlist_id = testCardList.getId();

        testCard = new Card();
        testCard.setName("firstCard");
        testCard.setDescription("12345");
        testCard.setArchived(false);


    }

    private void regenerateCardList() {
        testCardList = new CardList();
        testCardList.setName("regenerated");
        testCardList.setArchived(true);

    }

    private void regenerateCard()
    {
        testCard = new Card();
        testCard.setName("regenerated");
        testCard.setDescription("54321");
        testCard.setArchived(false);
    }

    @AfterEach
    public void cleaner() throws SQLException {
        try(Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM card;DELETE FROM cardlist; DELETE FROM board; DELETE FROM workspace");) {
            ps.execute();
        }
    }

    @Test
    public void create(){
        Card returned = cardService.create(cardlist_id, testCard);
        assertEquals(testCard, returned);
        assertAll(
                () -> assertEquals("firstCard", returned.getName()),
                () -> assertEquals("12345", returned.getDescription().intern()),
                () -> assertFalse(returned.getArchived())
        );
    }

    @Test
    public void readNotExisted()  {
        assertNull(cardService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        assertNull(cardService.create(UUID.randomUUID(), testCard));
    }

    @Test
    public void update(){
        cardService.create(cardlist_id, testCard);
        testCard.setName("Updated");
        testCard.setDescription("Updated");
        cardService.update(testCard);
        Card newCard = cardService.read(testCard.getId());
        assertNotNull(newCard.getUpdatedDate());
        assertEquals(testCard, newCard);
        assertAll(
                () -> assertEquals("Updated", newCard.getName()),
                () -> assertEquals("Updated", newCard.getDescription())
        );
    }

    @Test
    public void delete(){
        assertNull(cardService.read(testCard.getId()));
        cardService.create(cardlist_id, testCard);
        assertNotNull(cardService.read(testCard.getId()));
        cardService.delete(testCard.getId());
        assertNull(cardService.read(testCard.getId()));
    }

    @Test
    public void getAll(){
        List<Card> inMemory = new ArrayList<>();
        inMemory.add(testCard);
        cardService.create(cardlist_id, testCard);
        regenerateCard();
        inMemory.add(testCard);
        cardService.create(cardlist_id, testCard);
        assertEquals(inMemory, cardService.getAll());
    }

    @Test
    public void getParent(){
        CardList cardList1 = testCardList;
        UUID first = cardList1.getId();
        Card card1 = testCard;
        cardService.create(first, card1);
        regenerateCardList();
        regenerateCard();
        cardListService.create(board_id,testCardList);
        CardList cardList2 = testCardList;
        UUID second = cardList2.getId();
        Card card2 = testCard;
        cardService.create(second, card2);

        assertAll(
                () -> assertEquals(List.of(card1),cardService.getParent(first)),
                () -> assertEquals(List.of(card2),cardService.getParent(second))
        );
    }

    @Test
    public void getParentWithIllegalId() {
        assertEquals(cardService.getParent(UUID.randomUUID()), new ArrayList<>());
    }

}
