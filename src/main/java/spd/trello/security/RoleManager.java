package spd.trello.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spd.trello.domain.*;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.service.*;

import java.util.List;
import java.util.UUID;
@Component
public class RoleManager {
    @Autowired
    WorkspaceService workspaceService;
    @Autowired
    BoardService boardService;
    @Autowired
    CardListService cardListService;
    @Autowired
    CardService cardService;
    @Autowired
    CommentService commentService;
    @Autowired
    MemberService memberService;
    @Autowired
    UserService userService;
    @Autowired
    AttachmentService attachmentService;

    private static final Logger LOG = LoggerFactory.getLogger(RoleManager.class);

    private static final List<String> LINKS = List.of("attachments","comments","cards","cardlists","boards","workspaces","users");

    public Role getRole(String url, UUID id, User user)
    {
        int pointer = -1;
        UUID current = id;

        for (int i = 0; i < LINKS.size(); i++) {
            if (url.contains(LINKS.get(i)))
            {
                pointer = i;
                if (url.contains("create"))
                {
                    pointer-=1;
                }
                break;
            }
        }

        if (pointer == -1)
            return Role.ACCESS_DENIED;

        try {
            switch (pointer) {
                case 0:
                    current = attachmentService.read(current).getParentId();
                case 1:
                    current = commentService.read(current).getParentId();
                case 2:
                    current = cardService.read(current).getParentId();
                case 3:
                    current = cardListService.read(current).getParentId();
                case 4:
                    Board board = boardService.read(current);
                    if (pointer == 4) {
                        current = board.getParentId();
                    } else {
                        Role role = board.getMembers().stream().filter(m -> m.getParentId().equals(user.getId())).map(m -> m.getRole()).findFirst().orElse(board.getVisibility() == BoardVisibility.PRIVATE ? Role.ACCESS_DENIED : Role.GUEST);
                        LOG.info("???????????? ???????? ???? ?????????? - " + role.name());
                        return role;
                    }

                case 5:
                    Workspace workspace = workspaceService.read(current);
                    Role role = workspace.getMembers().stream().filter(m -> m.getParentId().equals(user.getId())).map(m -> m.getRole()).findFirst().orElse(workspace.getVisibility() == WorkspaceVisibility.PRIVATE ? Role.ACCESS_DENIED : Role.GUEST);
                    LOG.info("???????????? ???????? ???? ???????????????????? - " + role.name());
                    return role;

            }
        }
        catch (ObjectNotFoundException e)
        {
            return Role.ACCESS_DENIED;
        }

        return Role.ACCESS_DENIED;
    }

}
