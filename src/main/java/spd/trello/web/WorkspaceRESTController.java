package spd.trello.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spd.trello.domain.Workspace;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.service.WorkspaceService;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api")
public class WorkspaceRESTController {
    final WorkspaceService service;

    public WorkspaceRESTController(WorkspaceService service) {
        this.service = service;
    }

    @RequestMapping(value = "/workspaces")
    public List<Workspace> showAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/workspaces/create")
    public ResponseEntity<Workspace> Create(@RequestBody Workspace workspace) {
        return new ResponseEntity<>(service.create(workspace),HttpStatus.OK);
    }

    @RequestMapping(value = "/workspaces/update")
    public ResponseEntity Update(@RequestBody Workspace workspace) {
        if (service.read(workspace.getId()) == null)
            return new ResponseEntity(new ObjectNotFoundException(workspace.getId(),workspace.getClass()), HttpStatus.NOT_FOUND);
        service.update(workspace);
        return new ResponseEntity(service.read((workspace.getId())), HttpStatus.OK);
    }


    @RequestMapping("/workspaces/delete")
    public HttpStatus Delete(@RequestBody String id)
    {

        if (service.read(UUID.fromString(id)) == null)
            return HttpStatus.NOT_FOUND;
        service.delete(UUID.fromString(id));
        if (service.read(UUID.fromString(id)) == null)
            return HttpStatus.OK;
        else
            return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}