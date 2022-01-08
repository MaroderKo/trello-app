package spd.trello.web;

import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepository;
import spd.trello.service.WorkspaceService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class WorkspaceServlet extends HttpServlet {

    static WorkspaceService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action == null ? "all" : action)
        {
            case "create":
                req.setAttribute("action","create");
                req.setAttribute("workspace", new Workspace());
                req.getRequestDispatcher("/workspace_menu.jsp").forward(req,resp);
            case "open":
                req.setAttribute("workspace_id",req.getParameter("id"));
                resp.addCookie(new Cookie("workspace_id", req.getParameter("id")));
                req.getRequestDispatcher("/boards.jsp").forward(req, resp);
                break;

            case "update":
                req.setAttribute("action","update");
                req.setAttribute("workspace", service.read(UUID.fromString(req.getParameter("id"))));
                req.getRequestDispatcher("/workspace_menu.jsp").forward(req,resp);
                return;
            case "delete":
                break;

            default:
                req.setAttribute("workspaces",service.getAll());
                req.getRequestDispatcher("/workspaces.jsp").forward(req,resp);
        }
    }

    @Override
    public void init()  {
        service = new WorkspaceService(new WorkspaceRepository());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Workspace workspace;
        switch (action == null ? "all" : action)
        {
            case "update":
                workspace = service.read(UUID.fromString(req.getParameter("id")));
                workspace.setName(req.getParameter("name"));
                workspace.setDescription(req.getParameter("description"));
                workspace.setVisibility(WorkspaceVisibility.valueOf(req.getParameter("visibility")));
                service.update(workspace.getId(), workspace);
                resp.sendRedirect("workspaces");
                break;
            case "create":
                workspace = new Workspace();
                workspace.setName(req.getParameter("name"));
                workspace.setDescription(req.getParameter("description"));
                workspace.setVisibility(WorkspaceVisibility.valueOf(req.getParameter("visibility")));
                service.create(null, workspace);
                resp.sendRedirect("workspaces");
                break;

            default:
                resp.sendRedirect("workspaces");
                break;
        }
    }
}
