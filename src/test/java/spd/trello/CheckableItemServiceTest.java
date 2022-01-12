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

public class CheckableItemServiceTest extends BaseTest {
    AbstractService<Workspace> workspaceService = new WorkspaceService(new WorkspaceRepository());
    AbstractService<Board> boardService = new BoardService(new BoardRepository());
    AbstractService<CardList> cardListService = new CardListService(new CardListRepository());
    AbstractService<Card> cardService = new CardService(new CardRepository());
    AbstractService<CheckList> checkListService = new CheckListService(new CheckListRepository());
    AbstractService<CheckableItem> checkableItemService = new CheckableItemService(new CheckableItemRepository());
    UUID workspaceId;
    UUID boardId;
    UUID cardListId;
    UUID cardId;
    UUID checklistId;
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
        checkListService.create(cardId, testCheckList);
        checklistId = testCheckList.getId();

        testCheckableItem = new CheckableItem();
        testCheckableItem.setName("firstCheckableItem");
        testCheckableItem.setChecked(false);

    }

    private void regenerateCheckableItem() {
        testCheckableItem = new CheckableItem();
        testCheckableItem.setName("regenerated");
        testCheckableItem.setChecked(true);

    }

    private void regenerateCheckList() {
        testCheckList = new CheckList();
        testCheckList.setName("regenerated");

    }




    @AfterEach
    public void cleaner() throws SQLException {
        try(Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM checkable_item;DELETE FROM checklist;DELETE FROM card;DELETE FROM cardlist; DELETE FROM board; DELETE FROM workspace")) {
            ps.execute();
        }
    }

    @Test
    public void create() {
        CheckableItem returned = checkableItemService.create(checklistId, testCheckableItem);
        assertEquals(testCheckableItem, returned);
        assertAll(
                () -> assertEquals("firstCheckableItem", returned.getName())
        );
    }

    @Test
    public void readNotExisted() {
        assertNull(checkableItemService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        assertNull(checkableItemService.create(UUID.randomUUID(), testCheckableItem));
    }

    @Test
    public void update() {
        CheckableItem checkableItem = checkableItemService.create(checklistId, testCheckableItem);
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
        assertNull(checkableItemService.read(testCheckableItem.getId()));
        checkableItemService.create(checklistId, testCheckableItem);
        assertNotNull(checkableItemService.read(testCheckableItem.getId()));
        checkableItemService.delete(testCheckableItem.getId());
        assertNull(checkableItemService.read(testCheckableItem.getId()));
    }

    @Test
    public void getAll() {
        List<CheckableItem> inMemory = new ArrayList<>();
        inMemory.add(testCheckableItem);
        checkableItemService.create(checklistId, testCheckableItem);
        regenerateCheckableItem();
        inMemory.add(testCheckableItem);
        checkableItemService.create(checklistId, testCheckableItem);
        assertEquals(inMemory, checkableItemService.getAll());
    }



    @Test
    public void getParent() {
        CheckList checkList1 = testCheckList;
        UUID first = checkList1.getId();
        CheckableItem checkableItem1 = testCheckableItem;
        checkableItemService.create(first, checkableItem1);
        regenerateCheckList();
        regenerateCheckableItem();
        checkListService.create(cardId,testCheckList);
        CheckList checkList2 = testCheckList;
        UUID second = checkList2.getId();
        CheckableItem checkableItem2 = testCheckableItem;
        checkableItemService.create(second, checkableItem2);

        assertAll(
                () -> assertEquals(List.of(checkableItem1), checkableItemService.getParent(first)),
                () -> assertEquals(List.of(checkableItem2), checkableItemService.getParent(second))
        );
    }



    @Test
    public void getParentWithIllegalId() {
        assertEquals(checkableItemService.getParent(UUID.randomUUID()), new ArrayList<>());
    }
}
