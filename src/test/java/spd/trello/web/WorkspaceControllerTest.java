package spd.trello.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.service.WorkspaceService;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WorkspaceControllerTest extends BaseWebTest<Workspace> implements ControllerActionList {

    @Autowired
    @MockBean
    WorkspaceService service;

    @Override
    public void getAll() throws Exception {
        MvcResult mvcResult = showAll("/workspaces");
    }

    Workspace workspace = new Workspace();

    @BeforeEach
    public void initWorkspace() {
        workspace.setName("Test");
        workspace.setDescription("12354");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
    }

    @Test
    @Override
    public void createSuccess() throws Exception {
        when(service.create(workspace)).thenReturn(workspace);
        MvcResult result = create("/api/workspaces/create", workspace);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus()),
                () -> assertEquals(UUID.fromString((String) getValue(result, "$.id")), workspace.getId()),
                () -> assertEquals(getValue(result, "$.createdBy"), workspace.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), workspace.getName()),
                () -> assertEquals(getValue(result, "$.description"), workspace.getDescription()),
                () -> assertEquals(getValue(result, "$.visibility"), workspace.getVisibility().name()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(workspace.getCreatedDate()))

        );

    }

    @Test
    @Override
    public void getOne() throws Exception {
        when(service.read(workspace.getId())).thenReturn(workspace);

        MvcResult result = read("/api/workspaces/id/" + workspace.getId());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus()),
                () -> assertEquals(UUID.fromString((String) getValue(result, "$.id")), workspace.getId()),
                () -> assertEquals(getValue(result, "$.createdBy"), workspace.getCreatedBy()),
                () -> assertEquals(getValue(result, "$.name"), workspace.getName()),
                () -> assertEquals(getValue(result, "$.description"), workspace.getDescription()),
                () -> assertEquals(getValue(result, "$.visibility"), workspace.getVisibility().name()),
                () -> assertEquals(getValue(result, "$.createdDate"), Date(workspace.getCreatedDate()))

        );
    }

    @Test
    @Override
    public void getOneNotExisted() throws Exception {
        MvcResult result = read("/api/workspaces/id/" + workspace.getId());

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus()),
                () -> assertEquals("Resource not found", result.getResponse().getErrorMessage())
        );
    }

    @Test
    @Override
    public void updateSuccess() throws Exception {
        when(service.read(workspace.getId())).thenReturn(workspace);

        MvcResult result = update("/api/workspaces/update", workspace);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void updateNotExisted() throws Exception {
        MvcResult result = update("/api/workspaces/update", workspace);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }

    @Test
    @Override
    public void deleteSuccess() throws Exception {
        when(service.read(workspace.getId())).thenReturn(workspace).thenReturn(null).thenReturn(null).thenReturn(null).thenReturn(null);
        MvcResult result = delete("/api/workspaces/delete/" + workspace.getId());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @Override
    public void deleteNotExisted() throws Exception {
        MvcResult result = delete("/api/workspaces/delete/" + workspace.getId());
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        assertEquals("Resource not found", result.getResponse().getErrorMessage());

    }
}
