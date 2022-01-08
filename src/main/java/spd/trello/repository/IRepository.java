package spd.trello.repository;

import java.util.List;
import java.util.UUID;

public interface IRepository<T> {
    public abstract T create(UUID parent, T t);

    public abstract void delete(UUID id);

    public abstract void update(UUID id, T t);

    public abstract T read(UUID id);

    public List<T> getAll();

    public List<T> getParrent(UUID id);

}
