package spd.trello.web;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
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

    @RequestMapping("/workspace_menu")
    public HttpEntity<Workspace> edit(ModelAndView modelAndView) {
        return new ResponseEntity<>(service.read((UUID) modelAndView.getModel().get("workspace_id")), HttpStatus.OK);
    }

    @RequestMapping(value = "/workspaces/create", method = RequestMethod.GET)
    public Object Create(Model model) {
        model.addAttribute("workspace", new Workspace());
        model.addAttribute("action","create");
        return "workspace/menu";
    }
    @RequestMapping(value = "/workspaces/update", method = RequestMethod.GET)
    public String Update(Model model, @RequestParam("id") String id) {
        Workspace workspace = service.read(UUID.fromString(id));
        model.addAttribute("workspace", workspace);
        model.addAttribute("action","update");
        return "workspace/menu";
    }

    @RequestMapping(value = {"/workspaces/create", "/workspaces/update"},method = RequestMethod.POST)
    public String saveNew(@RequestParam("action") String action, @ModelAttribute("id") String id, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("visibility") String visibility)
    {
        Workspace workspace = new Workspace();
        workspace.setId(UUID.fromString(id));
        workspace.setName(name);
        workspace.setDescription(description);
        workspace.setVisibility(WorkspaceVisibility.valueOf(visibility));
        if (action.equals("create"))
            service.create(workspace);
        else if (action.equals("update"))
            service.update(workspace);
        return "workspace/list";

    }

    @RequestMapping("/workspaces/delete")
    public String Delete(@RequestParam("id") String id)
    {
        service.delete(UUID.fromString(id));
        return "workspace/list";
    }
}