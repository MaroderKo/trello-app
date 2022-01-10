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

public class CommentServiceTest extends BaseTest{
    AbstractService<Workspace> workspaceService = new WorkspaceService(new WorkspaceRepository());
    AbstractService<Board> boardService = new BoardService(new BoardRepository());
    AbstractService<CardList> cardListService = new CardListService(new CardListRepository());
    AbstractService<Card> cardService = new CardService(new CardRepository());
    AbstractService<Comment> commentService = new CommentService(new CommentRepository());
    UUID workspace_id;
    UUID board_id;
    UUID cardlist_id;
    UUID card_id;
    Workspace testWorkspace;
    Board testBoard;
    CardList testCardList;
    Card testCard;
    Comment testComment;


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

        testComment = new Comment();
        testComment.setAuthor("Dex");
        testComment.setArchived(false);
        testComment.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.");


    }

    private void regenerateComment() {
        testComment = new Comment();
        testComment.setAuthor("Dex");
        testComment.setArchived(false);
        testComment.setText("One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by a");

    }

    private void regenerateCard()
    {
        testCard = new Card();
        testCard.setName("regenerated");
        testCard.setDescription("54321");
        testCard.setArchived(false);
    }

    @AfterEach
    public void cleaner() throws SQLException {
        try(Connection connection = ConnectionPool.get().getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM comment;DELETE FROM card;DELETE FROM cardlist; DELETE FROM board; DELETE FROM workspace")) {
            ps.execute();
        }
    }

    @Test
    public void create(){
        Comment returned = commentService.create(card_id, testComment);
        assertEquals(testComment, returned);
        assertAll(
                () -> assertEquals("Dex", returned.getAuthor()),
                () -> assertEquals("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.", returned.getText().intern()),
                () -> assertFalse(returned.getArchived())
        );
    }

    @Test
    public void readNotExisted()  {
        assertNull(commentService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        assertNull(commentService.create(UUID.randomUUID(), testComment));
    }

    @Test
    public void update(){
        commentService.create(card_id, testComment);
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
    public void delete(){
        assertNull(commentService.read(testComment.getId()));
        commentService.create(card_id, testComment);
        assertNotNull(commentService.read(testComment.getId()));
        commentService.delete(testComment.getId());
        assertNull(commentService.read(testComment.getId()));
    }

    @Test
    public void getAll(){
        List<Comment> inMemory = new ArrayList<>();
        inMemory.add(testComment);
        commentService.create(card_id, testComment);
        regenerateComment();
        inMemory.add(testComment);
        commentService.create(card_id, testComment);
        assertEquals(inMemory, commentService.getAll());
    }

    @Test
    public void getParent(){
        Card parent1 = testCard;
        UUID first = parent1.getId();
        Comment child1 = testComment;
        commentService.create(first, child1);
        regenerateComment();
        regenerateCard();
        cardService.create(cardlist_id,testCard);
        Card parent2 = testCard;
        UUID second = parent2.getId();
        Comment child2 = testComment;
        commentService.create(second, child2);

        assertAll(
                () -> assertEquals(List.of(child1),commentService.getParent(first)),
                () -> assertEquals(List.of(child2),commentService.getParent(second))
        );
    }

    @Test
    public void getParentWithIllegalId() {
        assertEquals(cardService.getParent(UUID.randomUUID()), new ArrayList<>());
    }

}
