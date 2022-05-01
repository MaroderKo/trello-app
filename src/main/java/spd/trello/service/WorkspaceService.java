package spd.trello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;

@Service
public class WorkspaceService extends AbstractService<Workspace, WorkspaceRepository> {

    @Autowired
    MemberService memberService;

    public WorkspaceService(WorkspaceRepository repository) {
        super(repository);
    }

    @Override
    public Workspace create(Workspace workspace) {
        workspace.getMembers().add(memberService.ownerMember());
        return super.create(workspace);
    }
}
