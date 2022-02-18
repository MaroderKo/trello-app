package spd.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import spd.trello.domain.*;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.service.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest extends BaseTest {
    @Autowired
    WorkspaceService workspaceService;
    @Autowired
    BoardService boardService;
    @Autowired
    CardListService cardListService;
    @Autowired
    CardService cardService;
    @Autowired
    CommentService commentService;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;
    Comment testComment;


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

        testComment = new Comment();
        testComment.setAuthor("Dex");
        testComment.setArchived(false);
        testComment.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.");
        testComment.setParentId(testCard.getId());


    }

    private void regenerateComment() {
        testComment = new Comment();
        testComment.setAuthor("Dex");
        testComment.setArchived(false);
        testComment.setText("One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by a");
        testComment.setParentId(testCard.getId());

    }

    private void regenerateCard() {
        testCard = new Card();
        testCard.setName("regenerated");
        testCard.setDescription("54321");
        testCard.setArchived(false);
        testCard.setParentId(testCardList.getId());
    }

    @Test
    public void create() {
        Comment returned = commentService.create(testComment);
        assertEquals(testComment, returned);
        assertAll(
                () -> assertEquals("Dex", returned.getAuthor()),
                () -> assertEquals("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.", returned.getText().intern()),
                () -> assertFalse(returned.getArchived())
        );
    }

    @Test
    public void readNotExisted() {
        assertThrows(ObjectNotFoundException.class,() -> commentService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        testComment.setParentId(UUID.randomUUID());
        assertThrows(DataIntegrityViolationException.class, () -> commentService.create(testComment));
    }

    @Test
    public void update() {
        commentService.create(testComment);
        testComment.setText("Updated");
        commentService.update(testComment);
        Comment newComment = commentService.read(testComment.getId());
        assertNotNull(newComment.getUpdatedDate());
        assertEquals(testComment, newComment);
        assertAll(
                () -> assertEquals("Updated", newComment.getText())
        );
    }

    @Test
    public void delete() {
        assertThrows(ObjectNotFoundException.class,() -> commentService.read(testComment.getId()));
        commentService.create(testComment);
        assertNotNull(commentService.read(testComment.getId()));
        commentService.delete(testComment.getId());
        assertThrows(ObjectNotFoundException.class,() -> commentService.read(testComment.getId()));
    }

    @Test
    public void getAll() {
        List<Comment> inMemory = commentService.getAll();
        inMemory.add(testComment);
        commentService.create(testComment);
        regenerateComment();
        inMemory.add(testComment);
        commentService.create(testComment);
        assertEquals(inMemory, commentService.getAll());
    }

    @Test
    public void getParent() {
        Card parent1 = testCard;
        UUID first = parent1.getId();
        Comment child1 = testComment;
        commentService.create(child1);
        regenerateCard();
        regenerateComment();
        cardService.create(testCard);
        Card parent2 = testCard;
        UUID second = parent2.getId();
        Comment child2 = testComment;
        commentService.create(child2);

        assertAll(
                () -> assertEquals(List.of(child1), commentService.getParent(first)),
                () -> assertEquals(List.of(child2), commentService.getParent(second))
        );
    }

    @Test
    public void getParentWithIllegalId() {
        assertTrue(cardService.getParent(UUID.randomUUID()).isEmpty());
    }

}
