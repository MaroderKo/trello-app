package spd.trello.web.api;

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
}