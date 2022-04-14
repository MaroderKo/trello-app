package spd.trello.service;

import spd.trello.domain.Domain;
import spd.trello.domain.ParentBased;
import spd.trello.repository.AbstractParentBasedRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public abstract class AbstractParentBasedService<T extends Domain & ParentBased,R extends AbstractParentBasedRepository<T>> extends AbstractService<T,R>{
    protected AbstractParentBasedService(R repository) {
        super(repository);
    }
    public List<T> getParent(@NotNull UUID id) {
        return repository.findByParentId(id);
    }
}
