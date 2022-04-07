package spd.trello.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.service.BoardService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BoardControllerTest extends ParentBasedWebTest implements ControllerActionList {

    @MockBean
    BoardService service;

    @Override
    public void getAll() throws Exception {
        MvcResult mvcResult = showAll("/boards");
    }

    Board board;

    @BeforeEach
    public void initBoard() {
        board = new Board();
        board.setParentId(UUID.randomUUID());
        board.setName("testBoard");
        board.setArchived(false);
        board.setDescription("12345");
        board.setVisibility(BoardVisibility.WORKSPACE);
    }

    @Test
    @Override
    public void createSuccess() throws Exception {
        when(service.create(board)).thenReturn(board);
        MvcResult result = create("/api/boards/create", board);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(UUID.fromString((String) getValue(result, "$.id")), board.getId()),
                () -> assertEquals(getValue(result, "$.createdBy"), board.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), board.getName()),
                () -> assertEquals(getValue(result, "$.description"), board.getDescription()),
                () -> assertEquals(getValue(result, "$.visibility"), board.getVisibility().name()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(board.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), board.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), board.getArchived())

        );

    }

    @Test
    @Override
    public void getOne() throws Exception {
        when(service.read(board.getId())).thenReturn(board);

        MvcResult result = read("/api/boards/id/" + board.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(getValue(result, "$.id"), board.getId().toString()),
                () -> assertEquals(getValue(result, "$.createdBy"), board.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), board.getName()),
                () -> assertEquals(getValue(result, "$.description"), board.getDescription()),
                () -> assertEquals(getValue(result, "$.visibility"), board.getVisibility().name()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(board.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), board.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), board.getArchived()));

    }

    @Test
    @Override
    public void getOneNotExisted() throws Exception {
        MvcResult result = read("/api/boards/id/" + board.getId());

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus()),
                () -> assertEquals("Resource not found", result.getResponse().getErrorMessage())
        );
    }

    @Test
    @Override
    public void updateSuccess() throws Exception {
        when(service.read(board.getId())).thenReturn(board);
        MvcResult result = update("/api/boards/update", board);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void updateNotExisted() throws Exception {
        MvcResult result = update("/api/boards/update", board);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }

    @Test
    @Override
    public void deleteSuccess() throws Exception {
        when(service.read(board.getId())).thenReturn(board).thenReturn(null);
        MvcResult result = delete("/api/boards/delete/" + board.getId());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void deleteNotExisted() throws Exception {
        MvcResult result = delete("/api/boards/delete/" + board.getId());
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }
}
