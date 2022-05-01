package spd.trello.service;


import org.springframework.validation.annotation.Validated;
import spd.trello.domain.*;
import spd.trello.exception.ObjectNotFoundException;
import spd.trello.repository.AbstractRepository;
import spd.trello.security.SecurityConfig;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Validated
public abstract class AbstractService<T extends Domain, R extends AbstractRepository<T>> {

    protected R repository;

    protected AbstractService(R repository)
    {
        this.repository = repository;
    }

    @Transactional
    public T create(@Valid T t)
    {
        return repository.saveAndFlush(t);
    }
    public T read(@NotNull UUID id)
    {
        return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }
    public T update (@Valid T t)
    {
        if (t instanceof Resource)
        {
            ((Resource) t).setCreatedBy(SecurityConfig.getUserName());
            ((Resource) t).setUpdatedDate(LocalDateTime.now());
        }
        return repository.update(t);
    }
    public void delete(@NotNull UUID id)
    {
        repository.deleteById(id);
    }
    public List<T> getAll()
    {
        return repository.findAll();
    }




}
