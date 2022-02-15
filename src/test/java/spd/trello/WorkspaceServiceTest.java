package spd.trello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
public class WorkspaceServiceTest extends BaseTest {
    @Autowired
    static WorkspaceService workspaceService;
    static Workspace testWorkspace;

    @BeforeEach
    protected void workspaceInit() {
        Workspace workspace = new Workspace();
        workspace.setName("Test");
        workspace.setDescription("12354");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        testWorkspace = workspace;
    }


    @Test
    public void create() {
        Workspace returned = workspaceService.create(testWorkspace);
        assertEquals(testWorkspace, returned);
        assertAll(
                () -> assertEquals("Test", returned.getName()),
                () -> assertEquals("12354", returned.getDescription().intern()),
                () -> assertEquals(WorkspaceVisibility.PRIVATE, returned.getVisibility())
        );

    }

    @Test
    public void readNotExisted() {
        assertThrows(JpaObjectRetrievalFailureException.class,() -> workspaceService.read(UUID.randomUUID()));
    }

    @Test
    public void update() {
        workspaceService.create(testWorkspace);
        testWorkspace.setName("Updated");
        testWorkspace.setDescription("Updated");
        testWorkspace.setVisibility(WorkspaceVisibility.PUBLIC);
        assertThrows(JpaObjectRetrievalFailureException.class,() -> workspaceService.read(testWorkspace.getId()).getUpdatedDate());
        workspaceService.update(testWorkspace);
        Workspace newWorkspace = workspaceService.read(testWorkspace.getId());
        assertNotNull(newWorkspace.getUpdatedDate());
        assertEquals(testWorkspace, newWorkspace);
        assertAll(
                () -> assertEquals("Updated", newWorkspace.getName()),
                () -> assertEquals("Updated", newWorkspace.getDescription()),
                () -> assertEquals(WorkspaceVisibility.PUBLIC, newWorkspace.getVisibility())
        );
    }

    @Test
    public void delete() {
        workspaceService.create(testWorkspace);
        workspaceService.delete(testWorkspace.getId());
        assertThrows(JpaObjectRetrievalFailureException.class,() -> workspaceService.read(testWorkspace.getId()));
    }

    @Test
    public void getAll() {
        List<Workspace> inMemory = workspaceService.getAll();
        inMemory.add(testWorkspace);
        workspaceService.create(testWorkspace);
        workspaceInit();
        inMemory.add(testWorkspace);
        workspaceService.create(testWorkspace);
        assertEquals(inMemory, workspaceService.getAll());
    }

}
