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

public class CheckListServiceTest extends BaseTest {

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
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;
    CheckList testCheckList;

    @BeforeEach
    public void initObjects() {
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

    }

    private void regenerateCard() {
        testCard = new Card();
        testCard.setName("regenerated");
        testCard.setArchived(false);
        testCard.setDescription("54321");
        testCard.setParentId(testCardList.getId());

    }

    private void regenerateCheckList() {
        testCheckList = new CheckList();
        testCheckList.setName("regenerated");
        testCheckList.setParentId(testCard.getId());
    }


    @Test
    public void create() {
        CheckList returned = checkListService.create(testCheckList);
        assertEquals(testCheckList, returned);
        assertAll(
                () -> assertEquals("firstCheckList", returned.getName())
        );
    }

    @Test
    public void readNotExisted() {
        assertThrows(JpaObjectRetrievalFailureException.class,() -> checkListService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        testCheckList.setParentId(UUID.randomUUID());
        assertThrows(DataIntegrityViolationException.class, () -> checkListService.create(testCheckList));
    }

    @Test
    public void update() {
        CheckList checkList = checkListService.create(testCheckList);
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
        assertThrows(JpaObjectRetrievalFailureException.class,() -> checkListService.read(testCheckList.getId()));
        checkListService.create(testCheckList);
        assertNotNull(checkListService.read(testCheckList.getId()));
        checkListService.delete(testCheckList.getId());
        assertThrows(JpaObjectRetrievalFailureException.class,() -> checkListService.read(testCheckList.getId()));
    }

    @Test
    public void getAll() {
        List<CheckList> inMemory = checkListService.getAll();
        inMemory.add(testCheckList);
        checkListService.create(testCheckList);
        regenerateCheckList();
        inMemory.add(testCheckList);
        checkListService.create(testCheckList);
        assertEquals(inMemory, checkListService.getAll());
    }


    @Test
    public void getParent() {
        Card card1 = testCard;
        UUID first = card1.getId();
        CheckList checkList1 = testCheckList;
        checkListService.create(checkList1);
        regenerateCard();
        regenerateCheckList();
        cardService.create(testCard);
        Card card2 = testCard;
        UUID second = card2.getId();
        CheckList checkList2 = testCheckList;
        checkListService.create(checkList2);

        assertAll(
                () -> assertEquals(List.of(checkList1), checkListService.getParent(first)),
                () -> assertEquals(List.of(checkList2), checkListService.getParent(second))
        );
    }


    @Test
    public void getParentWithIllegalId() {
        assertTrue(checkListService.getParent(UUID.randomUUID()).isEmpty());
    }
}
