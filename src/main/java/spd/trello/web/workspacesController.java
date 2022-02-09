package spd.trello.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.service.WorkspaceService;

import java.util.UUID;

@Controller
public class workspacesController {
    final WorkspaceService service;

    public workspacesController(WorkspaceService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/workspaces")
    public String showAll(Model model) {
        model.addAttribute("workspaces", service.getAll());
        return "workspaces";
    }

    @RequestMapping("/workspace_menu")
    public String edit(ModelAndView modelAndView) {
        System.out.println((modelAndView.getModel().get("workspace_id")));
        modelAndView.addObject("workspace", service.read((UUID) modelAndView.getModel().get("workspace_id")));
        return "workspace_menu";
    }

    @RequestMapping(value = "/workspaces/create", method = RequestMethod.GET)
    public String Create(Model model) {
        model.addAttribute("workspace", new Workspace());
        model.addAttribute("action","create");
        System.out.println(model.getAttribute("workspace"));
        return "workspace_menu";
    }
    @RequestMapping(value = "/workspaces/update", method = RequestMethod.GET)
    public String Update(Model model, @RequestParam("id") String id) {
        Workspace workspace = service.read(UUID.fromString(id));
        model.addAttribute("workspace", workspace);
        model.addAttribute("action","update");
        return "workspace_menu";
    }

    @RequestMapping(value = {"/workspaces/create", "/workspaces/update"},method = RequestMethod.POST)
    public String saveNew(@RequestParam("action") String action,@ModelAttribute("id") String id, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("visibility") String visibility)
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
        return "redirect:/workspaces";

    }

    @RequestMapping("/workspaces/delete")
    public String Delete(@RequestParam("id") String id)
    {
        service.delete(UUID.fromString(id));
        return "redirect:/workspaces";
    }

}
