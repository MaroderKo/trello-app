package spd.trello.service;

import spd.trello.domain.Domain;
import spd.trello.repository.IRepository;

import java.util.List;
import java.util.UUID;

public abstract class AbstractService<T extends Domain> {

    protected IRepository<T> repository;

    protected AbstractService(IRepository<T> repository)
    {
        this.repository = repository;
    }

    public T create(UUID parent, T t)
    {
        return repository.create(parent, t);
    }
    public T read(UUID id)
    {
        return repository.read(id);
    }
    public void update (T t)
    {
        repository.update(t);
    }
    public void delete(UUID id)
    {
        repository.delete(id);
    }

    public void print(T t)
    {
        System.out.println(t.toString());
    }


    public List<T> getAll()
    {
        return repository.getAll();
    }
    public List<T> getParent(UUID id)
    {
        return repository.getParrent(id);
    }




}
