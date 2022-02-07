package spd.trello.web;

import org.springframework.web.bind.annotation.*;
import spd.trello.domain.Workspace;
import spd.trello.service.AbstractService;


@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceRESTController extends AbstractRESTController<Workspace> {
    public WorkspaceRESTController(AbstractService<Workspace> service) {
        super(service);
    }
}