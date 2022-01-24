package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Workspace;
import spd.trello.repository.AbstractRepository;

@Service
public class WorkspaceService extends AbstractService<Workspace> {

    public WorkspaceService(AbstractRepository<Workspace> repository) {
        super(repository);
    }







}
