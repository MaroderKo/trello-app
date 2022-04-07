package spd.trello.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Comment;
import spd.trello.service.CommentService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CommentControllerTest extends ParentBasedWebTest implements ControllerActionList {

    @MockBean
    CommentService service;

    @Override
    public void getAll() throws Exception {
        MvcResult mvcResult = showAll("/comments");
    }

    Comment comment;

    @BeforeEach
    public void initBoard() {
        comment = new Comment();
        comment.setAuthor("Dex");
        comment.setArchived(false);
        comment.setText("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium.");
        comment.setParentId(UUID.randomUUID());

    }

    @Test
    @Override
    public void createSuccess() throws Exception {
        when(service.create(comment)).thenReturn(comment);
        MvcResult result = create("/api/comments/create", comment);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(UUID.fromString((String) getValue(result, "$.id")), comment.getId()),
                () -> assertEquals(getValue(result, "$.createdBy"), comment.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(comment.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), comment.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), comment.getArchived()),
                () -> assertEquals(getValue(result, "$.text"), comment.getText())

        );

    }

    @Test
    @Override
    public void getOne() throws Exception {
        when(service.read(comment.getId())).thenReturn(comment);

        MvcResult result = read("/api/comments/id/" + comment.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(getValue(result, "$.id"), comment.getId().toString()),
                () -> assertEquals(getValue(result, "$.createdBy"), comment.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(comment.getCreatedDate())),
                () -> assertEquals(getValue(result, "$.parentId"), comment.getParentId().toString()),
                () -> assertEquals(getValue(result, "$.archived"), comment.getArchived()),
                () -> assertEquals(getValue(result, "$.text"), comment.getText()));

    }

    @Test
    @Override
    public void getOneNotExisted() throws Exception {
        MvcResult result = read("/api/comments/id/" + comment.getId());

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus()),
                () -> assertEquals("Resource not found", result.getResponse().getErrorMessage())
        );
    }

    @Test
    @Override
    public void updateSuccess() throws Exception {
        when(service.read(comment.getId())).thenReturn(comment);
        MvcResult result = update("/api/comments/update", comment);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void updateNotExisted() throws Exception {
        MvcResult result = update("/api/comments/update", comment);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }

    @Test
    @Override
    public void deleteSuccess() throws Exception {
        when(service.read(comment.getId())).thenReturn(comment).thenReturn(null);
        MvcResult result = delete("/api/comments/delete/" + comment.getId());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void deleteNotExisted() throws Exception {
        MvcResult result = delete("/api/comments/delete/" + comment.getId());
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }
}
