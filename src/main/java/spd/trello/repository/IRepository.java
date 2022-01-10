package spd.trello.repository;

import spd.trello.domain.Domain;

import java.util.List;
import java.util.UUID;

public interface IRepository<T extends Domain> {
    public abstract T create(UUID parent, T t);

    public abstract void delete(UUID id);

    public abstract void update(T t);

    public abstract T read(UUID id);

    public List<T> getAll();

    public List<T> getParrent(UUID id);

}
