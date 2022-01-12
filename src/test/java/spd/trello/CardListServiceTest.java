package spd.trello;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.db.ConnectionPool;
import spd.trello.domain.*;
import spd.trello.repository.BoardRepository;
import spd.trello.repository.CardListRepository;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.AbstractService;
import spd.trello.service.BoardService;
import spd.trello.service.CardListService;
import spd.trello.service.WorkspaceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardListServiceTest extends BaseTest {

    AbstractService<Workspace> workspaceService = new WorkspaceService(new WorkspaceRepository());
    AbstractService<Board> boardService = new BoardService(new BoardRepository());
    AbstractService<CardList> cardListService = new CardListService(new CardListRepository());
    UUID workspaceId;
    UUID boardId;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;

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
    }

    private void regenerateBoard() {
        testBoard = new Board();
        testBoard.setName("regenerated");
        testBoard.setArchived(false);
        testBoard.setDescription("54321");
        testBoard.setVisibility(BoardVisibility.PUBLIC);
        boardId = testBoard.getId();

    }

    private void regenerateCardList() {
        testCardList = new CardList();
        testCardList.setName("regenerated");
        testCardList.setArchived(true);

    }




    @AfterEach
    public void cleaner() throws SQLException {
        try(Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM cardlist; DELETE FROM board; DELETE FROM workspace")) {
            ps.execute();
        }
    }

    @Test
    public void create() {
        CardList returned = cardListService.create(boardId, testCardList);
        assertEquals(testCardList, returned);
        assertAll(
                () -> assertEquals("firstCardList", returned.getName()),
                () -> assertFalse(returned.getArchived())
        );
    }

    @Test
    public void readNotExisted() {
        assertNull(cardListService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        assertNull(cardListService.create(UUID.randomUUID(), testCardList));
    }

    @Test
    public void update() {
        CardList cardList = cardListService.create(boardId, testCardList);
        cardList.setName("update");
        cardList.setArchived(true);
        cardListService.update(cardList);
        CardList updated = cardListService.read(cardList.getId());
        assertEquals(cardList, updated);
        assertAll(
                () -> assertEquals(cardList.getName(), updated.getName()),
                () -> assertEquals(cardList.getArchived(), updated.getArchived())
        );
    }

    @Test
    public void delete() {
        assertNull(cardListService.read(testCardList.getId()));
        cardListService.create(boardId, testCardList);
        assertNotNull(cardListService.read(testCardList.getId()));
        cardListService.delete(testCardList.getId());
        assertNull(cardListService.read(testCardList.getId()));
    }

    @Test
    public void getAll() {
        List<CardList> inMemory = new ArrayList<>();
        inMemory.add(testCardList);
        cardListService.create(boardId, testCardList);
        regenerateCardList();
        inMemory.add(testCardList);
        cardListService.create(boardId, testCardList);
        assertEquals(inMemory, cardListService.getAll());
    }



    @Test
    public void getParent() {
        Board board1 = testBoard;
        UUID first = board1.getId();
        CardList cardList1 = testCardList;
        cardListService.create(first, cardList1);
        regenerateBoard();
        regenerateCardList();
        boardService.create(workspaceId,testBoard);
        Board board2 = testBoard;
        UUID second = board2.getId();
        CardList cardList2 = testCardList;
        cardListService.create(second, cardList2);

        assertAll(
                () -> assertEquals(List.of(cardList1), cardListService.getParent(first)),
                () -> assertEquals(List.of(cardList2), cardListService.getParent(second))
        );
    }



    @Test
    public void getParentWithIllegalId() {
        assertEquals(cardListService.getParent(UUID.randomUUID()), new ArrayList<>());
    }
}
