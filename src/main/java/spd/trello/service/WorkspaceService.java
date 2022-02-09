package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Workspace;
import spd.trello.repository.WorkspaceRepository;

@Service
public class WorkspaceService extends AbstractService<Workspace, WorkspaceRepository> {

    public WorkspaceService(WorkspaceRepository repository) {
        super(repository);
    }







}
