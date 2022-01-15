package spd.trello;

import org.h2.message.DbException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.BoardRepository;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.AbstractService;
import spd.trello.service.BoardService;
import spd.trello.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BoardServiceTest extends BaseTest{
    AbstractService<Workspace> workspaceService = new WorkspaceService(new WorkspaceRepository());
    AbstractService<Board> boardService = new BoardService(new BoardRepository());
    Board testBoard;
    Workspace testWorkspace;

    @BeforeEach
    public void initObjects()
    {


        testWorkspace = new Workspace();
        testWorkspace.setName("Test");
        testWorkspace.setDescription("12354");
        testWorkspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspaceService.create(testWorkspace);

        testBoard = new Board();
        testBoard.setWorkspaceId(testWorkspace.getId());
        testBoard.setName("testBoard");
        testBoard.setArchived(false);
        testBoard.setDescription("12345");
        testBoard.setVisibility(BoardVisibility.WORKSPACE);
        testBoard.setWorkspaceId(testWorkspace.getId());
    }

    public void regenerateWorkspace()
    {
        testWorkspace = new Workspace();
    }

    public void regenerateBoard()
    {
        testBoard = new Board();
        testBoard.setName("testBoard");
        testBoard.setArchived(false);
        testBoard.setDescription("12345");
        testBoard.setVisibility(BoardVisibility.PRIVATE);
        testBoard.setWorkspaceId(testWorkspace.getId());
    }


    @Test
    public void create(){
        Board returned = boardService.create(testBoard);
        assertEquals(testBoard, returned);
        assertAll(
                () -> assertEquals("testBoard", returned.getName()),
                () -> assertEquals("12345", returned.getDescription().intern()),
                () -> assertEquals(BoardVisibility.WORKSPACE, returned.getVisibility()),
                () -> assertFalse(returned.getArchived())
        );
    }

    @Test
    public void readNotExisted()  {
        assertNull(boardService.read(UUID.randomUUID()));
    }

    @Test
    public void createWithIllegalId() {
        testBoard.setWorkspaceId(UUID.randomUUID());
        assertThrows(DataIntegrityViolationException.class,() -> boardService.create(testBoard));
    }

    @Test
    public void update(){
        boardService.create(testBoard);
        testBoard.setName("Updated");
        testBoard.setDescription("Updated");
        testBoard.setVisibility(BoardVisibility.PUBLIC);
        boardService.update(testBoard);
        Board newBoard = boardService.read(testBoard.getId());
        assertNotNull(newBoard.getUpdatedDate());
        assertEquals(testBoard, newBoard);
        assertAll(
                () -> assertEquals("Updated", newBoard.getName()),
                () -> assertEquals("Updated", newBoard.getDescription()),
                () -> assertEquals(BoardVisibility.PUBLIC, newBoard.getVisibility())
        );
    }

    @Test
    public void delete(){
        boardService.create(testBoard);
        boardService.delete(testBoard.getId());
        assertNull(boardService.read(testBoard.getId()));
    }

    @Test
    public void getAll(){
        List<Board> inMemory = boardService.getAll();
        inMemory.add(testBoard);
        boardService.create(testBoard);
        regenerateBoard();
        inMemory.add(testBoard);
        boardService.create(testBoard);
        assertEquals(inMemory, boardService.getAll());
    }

    @Test
    public void getParent(){
        Workspace workspace1 = testWorkspace;
        UUID first = workspace1.getId();
        Board board1 = testBoard;
        boardService.create(board1);
        regenerateWorkspace();
        regenerateBoard();
        workspaceService.create(testWorkspace);
        Workspace workspace2 = testWorkspace;
        UUID second = workspace2.getId();
        Board board2 = testBoard;
        boardService.create(board2);

        assertAll(
                () -> assertEquals(List.of(board1),boardService.getParent(first)),
                () -> assertEquals(List.of(board2),boardService.getParent(second))
    );
    }

    @Test
    public void getParentWithIllegalId() {
        assertTrue(boardService.getParent(UUID.randomUUID()).isEmpty());
    }
}
