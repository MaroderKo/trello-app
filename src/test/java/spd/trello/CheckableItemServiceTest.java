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
    UUID workspace_id;
    UUID board_id;
    UUID cardlist_id;
    UUID card_id;
    UUID checklist_id;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;
    CheckList testCheckList;
    CheckableItem testCheckableItem;

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
        checkListService.create(card_id, testCheckList);
        checklist_id = testCheckList.getId();

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
        CheckableItem returned = checkableItemService.create(checklist_id, testCheckableItem);
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
        CheckableItem checkableItem = checkableItemService.create(checklist_id, testCheckableItem);
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
        checkableItemService.create(checklist_id, testCheckableItem);
        assertNotNull(checkableItemService.read(testCheckableItem.getId()));
        checkableItemService.delete(testCheckableItem.getId());
        assertNull(checkableItemService.read(testCheckableItem.getId()));
    }

    @Test
    public void getAll() {
        List<CheckableItem> inMemory = new ArrayList<>();
        inMemory.add(testCheckableItem);
        checkableItemService.create(checklist_id, testCheckableItem);
        regenerateCheckableItem();
        inMemory.add(testCheckableItem);
        checkableItemService.create(checklist_id, testCheckableItem);
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
        checkListService.create(card_id,testCheckList);
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
