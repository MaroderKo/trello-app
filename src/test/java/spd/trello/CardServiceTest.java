package spd.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import spd.trello.domain.*;
import spd.trello.service.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class CardServiceTest extends BaseTest{
    @Autowired
    WorkspaceService workspaceService;
    @Autowired
    BoardService boardService;
    @Autowired
    CardListService cardListService;
    @Autowired
    CardService cardService;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;


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

    }

    private void regenerateCardList() {
        testCardList = new CardList();
        testCardList.setName("regenerated");
        testCardList.setArchived(true);
        testCardList.setParentId(testBoard.getId());
    }

    private void regenerateCard()
    {
        testCard = new Card();
        testCard.setName("regenerated");
        testCard.setDescription("54321");
        testCard.setArchived(false);
        testCard.setParentId(testCardList.getId());
    }


    @Test
    public void create(){
        Card returned = cardService.create(testCard);
        assertEquals(testCard, returned);
        assertAll(
                () -> assertEquals("firstCard", returned.getName()),
                () -> assertEquals("12345", returned.getDescription().intern()),
                () -> assertFalse(returned.getArchived())
        );
    }

    @Test
    public void readNotExisted()  {
        assertThrows(JpaObjectRetrievalFailureException.class,() -> cardService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        testCard.setParentId(UUID.randomUUID());
        assertThrows(DataIntegrityViolationException.class,() -> cardService.create(testCard));
    }

    @Test
    public void update(){
        cardService.create(testCard);
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
        assertThrows(JpaObjectRetrievalFailureException.class,() -> cardService.read(testCard.getId()));
        cardService.create(testCard);
        assertNotNull(cardService.read(testCard.getId()));
        cardService.delete(testCard.getId());
        assertThrows(JpaObjectRetrievalFailureException.class,() -> cardService.read(testCard.getId()));
    }

    @Test
    public void getAll(){
        List<Card> inMemory = cardService.getAll();
        inMemory.add(testCard);
        cardService.create(testCard);
        regenerateCard();
        inMemory.add(testCard);
        cardService.create(testCard);
        assertEquals(inMemory, cardService.getAll());
    }

    @Test
    public void getParent(){
        CardList cardList1 = testCardList;
        UUID first = cardList1.getId();
        Card card1 = testCard;
        cardService.create(card1);
        regenerateCardList();
        regenerateCard();
        cardListService.create(testCardList);
        CardList cardList2 = testCardList;
        UUID second = cardList2.getId();
        Card card2 = testCard;
        cardService.create(card2);

        assertAll(
                () -> assertEquals(List.of(card1),cardService.getParent(first)),
                () -> assertEquals(List.of(card2),cardService.getParent(second))
        );
    }

    @Test
    public void getParentWithIllegalId() {
        assertTrue(cardService.getParent(UUID.randomUUID()).isEmpty());
    }

}
