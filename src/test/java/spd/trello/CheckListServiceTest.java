package spd.trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.*;
import spd.trello.repository.*;
import spd.trello.service.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CheckListServiceTest extends BaseTest {

    AbstractService<Workspace> workspaceService = new WorkspaceService(new WorkspaceRepository());
    AbstractService<Board> boardService = new BoardService(new BoardRepository());
    AbstractService<CardList> cardListService = new CardListService(new CardListRepository());
    AbstractService<Card> cardService = new CardService(new CardRepository());
    AbstractService<CheckList> checkListService = new CheckListService(new CheckListRepository());
    UUID workspace_id;
    UUID board_id;
    UUID cardlist_id;
    UUID card_id;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;
    CheckList testCheckList;

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
        cardService.create(cardlist_id,testCard);
        card_id = testCard.getId();


        testCheckList = new CheckList();
        testCheckList.setName("firstCheckList");

    }

    private void regenerateCard() {
        testCard = new Card();
        testCard.setName("regenerated");
        testCard.setArchived(false);
        testCard.setDescription("54321");
        card_id = testCard.getId();

    }

    private void regenerateCheckList() {
        testCheckList = new CheckList();
        testCheckList.setName("regenerated");

    }




    @AfterEach
    public void cleaner() throws SQLException {
        try(Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM checklist;DELETE FROM card;DELETE FROM cardlist; DELETE FROM board; DELETE FROM workspace");) {
            ps.execute();
        }
    }

    @Test
    public void create() {
        CheckList returned = checkListService.create(card_id, testCheckList);
        assertEquals(testCheckList, returned);
        assertAll(
                () -> assertEquals("firstCheckList", returned.getName())
        );
    }

    @Test
    public void readNotExisted() {
        assertNull(checkListService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        assertNull(checkListService.create(UUID.randomUUID(), testCheckList));
    }

    @Test
    public void update() {
        CheckList checkList = checkListService.create(card_id, testCheckList);
        checkList.setName("update");
        checkListService.update(checkList);
        CheckList updated = checkListService.read(checkList.getId());
        assertEquals(checkList, updated);
        assertAll(
                () -> assertEquals(checkList.getName(), updated.getName())
        );
    }

    @Test
    public void delete() {
        assertNull(checkListService.read(testCheckList.getId()));
        checkListService.create(card_id, testCheckList);
        assertNotNull(checkListService.read(testCheckList.getId()));
        checkListService.delete(testCheckList.getId());
        assertNull(checkListService.read(testCheckList.getId()));
    }

    @Test
    public void getAll() {
        List<CheckList> inMemory = new ArrayList<>();
        inMemory.add(testCheckList);
        checkListService.create(card_id, testCheckList);
        regenerateCheckList();
        inMemory.add(testCheckList);
        checkListService.create(card_id, testCheckList);
        assertEquals(inMemory, checkListService.getAll());
    }



    @Test
    public void getParent() {
        Card card1 = testCard;
        UUID first = card1.getId();
        CheckList checkList1 = testCheckList;
        checkListService.create(first, checkList1);
        regenerateCheckList();
        regenerateCard();
        cardService.create(cardlist_id,testCard);
        Card card2 = testCard;
        UUID second = card2.getId();
        CheckList checkList2 = testCheckList;
        checkListService.create(second, checkList2);

        assertAll(
                () -> assertEquals(List.of(checkList1), checkListService.getParent(first)),
                () -> assertEquals(List.of(checkList2), checkListService.getParent(second))
        );
    }



    @Test
    public void getParentWithIllegalId() {
        assertEquals(checkListService.getParent(UUID.randomUUID()), new ArrayList<>());
    }
}
