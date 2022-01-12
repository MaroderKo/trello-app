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
    UUID workspaceId;
    UUID boardId;
    UUID cardListId;
    UUID cardId;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;
    CheckList testCheckList;

    @BeforeEach
    public void initObjects()
    {
        testWorkspace = new Workspace();
        testWorkspace.setName("Test");
        testWorkspace.setDescription("12354");
        testWorkspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspaceService.create(null, testWorkspace);
        workspaceId = testWorkspace.getId();

        testBoard = new Board();
        testBoard.setName("testBoard");
        testBoard.setArchived(false);
        testBoard.setDescription("12345");
        testBoard.setVisibility(BoardVisibility.WORKSPACE);
        boardService.create(workspaceId,testBoard);
        boardId = testBoard.getId();

        testCardList = new CardList();
        testCardList.setName("firstCardList");
        testCardList.setArchived(false);
        cardListService.create(boardId, testCardList);
        cardListId = testCardList.getId();

        testCard = new Card();
        testCard.setName("firstCard");
        testCard.setDescription("12345");
        testCard.setArchived(false);
        cardService.create(cardListId,testCard);
        cardId = testCard.getId();


        testCheckList = new CheckList();
        testCheckList.setName("firstCheckList");

    }

    private void regenerateCard() {
        testCard = new Card();
        testCard.setName("regenerated");
        testCard.setArchived(false);
        testCard.setDescription("54321");
        cardId = testCard.getId();

    }

    private void regenerateCheckList() {
        testCheckList = new CheckList();
        testCheckList.setName("regenerated");

    }




    @AfterEach
    public void cleaner() throws SQLException {
        try(Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM checklist;DELETE FROM card;DELETE FROM cardlist; DELETE FROM board; DELETE FROM workspace")) {
            ps.execute();
        }
    }

    @Test
    public void create() {
        CheckList returned = checkListService.create(cardId, testCheckList);
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
        CheckList checkList = checkListService.create(cardId, testCheckList);
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
        checkListService.create(cardId, testCheckList);
        assertNotNull(checkListService.read(testCheckList.getId()));
        checkListService.delete(testCheckList.getId());
        assertNull(checkListService.read(testCheckList.getId()));
    }

    @Test
    public void getAll() {
        List<CheckList> inMemory = new ArrayList<>();
        inMemory.add(testCheckList);
        checkListService.create(cardId, testCheckList);
        regenerateCheckList();
        inMemory.add(testCheckList);
        checkListService.create(cardId, testCheckList);
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
        cardService.create(cardListId,testCard);
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
