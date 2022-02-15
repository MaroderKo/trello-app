package spd.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import spd.trello.domain.*;
import spd.trello.service.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CheckableItemServiceTest extends BaseTest {
    @Autowired
    WorkspaceService workspaceService;
    @Autowired
    BoardService boardService;
    @Autowired
    CardListService cardListService;
    @Autowired
    CardService cardService;
    @Autowired
    CheckListService checkListService;
    @Autowired
    CheckableItemService checkableItemService;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;
    CheckList testCheckList;
    CheckableItem testCheckableItem;

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
        cardListService.create(testCardList);

        testCard = new Card();
        testCard.setName("firstCard");
        testCard.setDescription("12345");
        testCard.setArchived(false);
        testCard.setParentId(testCardList.getId());
        cardService.create(testCard);


        testCheckList = new CheckList();
        testCheckList.setName("firstCheckList");
        testCheckList.setParentId(testCard.getId());
        checkListService.create(testCheckList);

        testCheckableItem = new CheckableItem();
        testCheckableItem.setName("firstCheckableItem");
        testCheckableItem.setChecked(false);
        testCheckableItem.setParentId(testCheckList.getId());

    }

    private void regenerateCheckableItem() {
        testCheckableItem = new CheckableItem();
        testCheckableItem.setName("regenerated");
        testCheckableItem.setChecked(true);
        testCheckableItem.setParentId(testCheckList.getId());

    }

    private void regenerateCheckList() {
        testCheckList = new CheckList();
        testCheckList.setName("regenerated");
        testCheckList.setParentId(testCard.getId());

    }




    @Test
    public void create() {
        CheckableItem returned = checkableItemService.create(testCheckableItem);
        assertEquals(testCheckableItem, returned);
        assertAll(
                () -> assertEquals("firstCheckableItem", returned.getName())
        );
    }

    @Test
    public void readNotExisted() {
        assertThrows(JpaObjectRetrievalFailureException.class,() -> checkableItemService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        testCheckableItem.setParentId(UUID.randomUUID());
        assertThrows(DataIntegrityViolationException.class,() -> checkableItemService.create(testCheckableItem));
    }

    @Test
    public void update() {
        CheckableItem checkableItem = checkableItemService.create(testCheckableItem);
        checkableItem.setName("update");
        checkableItemService.update(checkableItem);
        CheckableItem updated = checkableItemService.read(checkableItem.getId());
        assertEquals(checkableItem, updated);
        assertAll(
                () -> assertEquals(checkableItem.getName(), updated.getName())
        );
    }

    @Test
    public void delete() {
        assertThrows(JpaObjectRetrievalFailureException.class,() -> checkableItemService.read(testCheckableItem.getId()));
        checkableItemService.create(testCheckableItem);
        assertNotNull(checkableItemService.read(testCheckableItem.getId()));
        checkableItemService.delete(testCheckableItem.getId());
        assertThrows(JpaObjectRetrievalFailureException.class,() -> checkableItemService.read(testCheckableItem.getId()));
    }

    @Test
    public void getAll() {
        List<CheckableItem> inMemory = checkableItemService.getAll();
        inMemory.add(testCheckableItem);
        checkableItemService.create(testCheckableItem);
        regenerateCheckableItem();
        inMemory.add(testCheckableItem);
        checkableItemService.create(testCheckableItem);
        assertEquals(inMemory, checkableItemService.getAll());
    }



    @Test
    public void getParent() {
        CheckList checkList1 = testCheckList;
        UUID first = checkList1.getId();
        CheckableItem checkableItem1 = testCheckableItem;
        checkableItemService.create(checkableItem1);
        regenerateCheckList();
        regenerateCheckableItem();
        checkListService.create(testCheckList);
        CheckList checkList2 = testCheckList;
        UUID second = checkList2.getId();
        CheckableItem checkableItem2 = testCheckableItem;
        checkableItemService.create(checkableItem2);

        assertAll(
                () -> assertEquals(List.of(checkableItem1), checkableItemService.getParent(first)),
                () -> assertEquals(List.of(checkableItem2), checkableItemService.getParent(second))
        );
    }



    @Test
    public void getParentWithIllegalId() {
        assertTrue(checkableItemService.getParent(UUID.randomUUID()).isEmpty());
    }
}
