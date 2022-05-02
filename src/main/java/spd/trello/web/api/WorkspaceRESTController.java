package spd.trello.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.AbstractService;


@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceRESTController extends AbstractRESTController<Workspace, WorkspaceRepository> {
    public WorkspaceRESTController(AbstractService<Workspace, WorkspaceRepository> service) {
        super(service);
    }

    @Override
    @PreAuthorize("true")
    public ResponseEntity<Workspace> Create(@RequestBody Workspace workspace) {
        System.out.println(workspace);
        return super.Create(workspace);
    }
}