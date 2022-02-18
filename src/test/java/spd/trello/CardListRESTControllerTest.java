package spd.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import spd.trello.domain.*;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.service.BoardService;
import spd.trello.service.CardListService;
import spd.trello.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CardListRESTControllerTest extends BaseTest {
    @Autowired
    WorkspaceService workspaceService;
    @Autowired
    BoardService boardService;
    @Autowired
    CardListService cardListService;
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
        workspaceService.create(testWorkspace);


        testBoard = new Board();
        testBoard.setName("testBoard");
        testBoard.setArchived(false);
        testBoard.setDescription("12345");
        testBoard.setVisibility(BoardVisibility.WORKSPACE);
        testBoard.setParentId(testWorkspace.getId());
        boardService.create(testBoard);

        testCardList = new CardList();
        testCardList.setName("firstCardList");
        testCardList.setArchived(false);
        testCardList.setParentId(testBoard.getId());
    }

    private void regenerateBoard() {
        testBoard = new Board();
        testBoard.setName("regenerated");
        testBoard.setArchived(false);
        testBoard.setDescription("54321");
        testBoard.setVisibility(BoardVisibility.PUBLIC);
        testBoard.setParentId(testWorkspace.getId());

    }

    private void regenerateCardList() {
        testCardList = new CardList();
        testCardList.setName("regenerated");
        testCardList.setArchived(true);
        testCardList.setParentId(testBoard.getId());
        testCardList.setParentId(testBoard.getId());
    }





    @Test
    public void create() {
        CardList returned = cardListService.create(testCardList);
        assertEquals(testCardList, returned);
        assertAll(
                () -> assertEquals("firstCardList", returned.getName()),
                () -> assertFalse(returned.getArchived())
        );
    }

    @Test
    public void readNotExisted() {
        assertThrows(ObjectNotFoundException.class,() -> cardListService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        testCardList.setParentId(UUID.randomUUID());
        assertThrows(DataIntegrityViolationException.class,() -> cardListService.create(testCardList));
    }

    @Test
    public void update() {
        CardList cardList = cardListService.create(testCardList);
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
        assertThrows(ObjectNotFoundException.class,() -> cardListService.read(testCardList.getId()));
        cardListService.create(testCardList);
        assertNotNull(cardListService.read(testCardList.getId()));
        cardListService.delete(testCardList.getId());
        assertThrows(ObjectNotFoundException.class,() -> cardListService.read(testCardList.getId()));
    }

    @Test
    public void getAll() {
        List<CardList> inMemory = cardListService.getAll();
        inMemory.add(testCardList);
        cardListService.create(testCardList);
        regenerateCardList();
        inMemory.add(testCardList);
        cardListService.create(testCardList);
        assertEquals(inMemory, cardListService.getAll());
    }



    @Test
    public void getParent() {
        Board board1 = testBoard;
        UUID first = board1.getId();
        CardList cardList1 = testCardList;
        cardListService.create(cardList1);
        regenerateBoard();
        regenerateCardList();
        boardService.create(testBoard);
        Board board2 = testBoard;
        UUID second = board2.getId();
        CardList cardList2 = testCardList;
        cardListService.create(cardList2);

        assertAll(
                () -> assertEquals(List.of(cardList1), cardListService.getParent(first)),
                () -> assertEquals(List.of(cardList2), cardListService.getParent(second))
        );
    }



    @Test
    public void getParentWithIllegalId() {
        assertTrue(cardListService.getParent(UUID.randomUUID()).isEmpty());
    }
}
