package spd.trello.web;

import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.domain.Workspace;
import spd.trello.repository.BoardRepository;
import spd.trello.service.BoardService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class BoardServlet extends HttpServlet {

    static BoardService service;

    @Override
    public void init()  {
        service = new BoardService(new BoardRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action == null ? "all" : action)
        {
            /*case "open":
                //int workspace_id = (int) req.getAttribute("workspace_id");
                req.getRequestDispatcher("/boards.jsp").forward(req, resp);
*/
            case "create":
                req.setAttribute("action","create");
                req.setAttribute("board", new Board());
                req.getRequestDispatcher("/board_menu.jsp").forward(req,resp);
            case "update":
                req.setAttribute("action","update");
                req.setAttribute("board", service.read(UUID.fromString(req.getParameter("id"))));
                req.getRequestDispatcher("/board_menu.jsp").forward(req,resp);
                return;

            case "delete":
                service.delete(UUID.fromString(req.getParameter("id")));
                Optional<Cookie> workspace_id1 = Arrays.stream(req.getCookies()).filter(c -> c.getName().equals("workspace_id")).findFirst();
                if (workspace_id1.isPresent()) {
                    resp.sendRedirect("boards?workspace_id=" + workspace_id1.get().getValue());
                } else {
                    resp.sendRedirect("workspaces");
                }
                break;


            default:
                UUID workspaceid = UUID.fromString(req.getParameter("workspace_id"));
                Optional<Cookie> optional_wid = Arrays.stream(req.getCookies()).filter(c -> c.getName().equals("workspace_id")).findFirst();
                if (optional_wid.isPresent()) {
                    System.out.println(1);
                    Cookie whitelistId = optional_wid.get();
                    if (!whitelistId.getValue().equals(workspaceid.toString())) {
                        System.out.println(2);
                        whitelistId.setValue(workspaceid.toString());
                        resp.addCookie(whitelistId);
                        resp.sendRedirect("boards?workspace_id="+workspaceid);
                        return;
                    }



                }
                else
                {
                    System.out.println(3);
                    Cookie workspace_id = new Cookie("workspace_id", workspaceid.toString());
                    resp.addCookie(workspace_id);
                    resp.sendRedirect("boards?workspace_id="+workspaceid);
                    return;
                }
                req.setAttribute("boards", service.getParent(workspaceid));
                req.getRequestDispatcher("/boards.jsp").forward(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Board board;
        Optional<Cookie> optional_w_id = Arrays.stream(req.getCookies()).filter(c -> c.getName().equals("workspace_id")).findFirst();
        switch (action == null ? "all" : action)
        {
            case "update":
                board = service.read(UUID.fromString(req.getParameter("id")));
                board.setName(req.getParameter("name"));
                board.setDescription(req.getParameter("description"));
                board.setVisibility(BoardVisibility.valueOf(req.getParameter("visibility")));
                board.setArchived(req.getParameter("archived") != null);
                System.out.println("archived: "+req.getParameter("archived"));
                service.update(board);
                resp.sendRedirect("boards");
                break;
            case "create":
                board = new Board();
                board.setName(req.getParameter("name"));
                board.setDescription(req.getParameter("description"));
                board.setVisibility(BoardVisibility.valueOf(req.getParameter("visibility")));
                board.setArchived(req.getParameter("archived") != null);

                if (optional_w_id.isPresent()) {
                    service.create(UUID.fromString(optional_w_id.get().getValue()), board);
                    resp.sendRedirect("boards?workspace_id="+optional_w_id.get().getValue());
                    break;
                }

            default:
                resp.sendRedirect("boards?workspace_id="+optional_w_id.get().getValue());
                break;
        }
    }
}
