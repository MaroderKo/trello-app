package spd.trello.repository;

import spd.trello.domain.Domain;

import java.util.List;
import java.util.UUID;

public interface IRepository<T extends Domain> {
    T create(UUID parent, T t);

    void delete(UUID id);

    T update(T t);

    T getById(UUID id);

    List<T> getAll();

    List<T> getParrent(UUID id);

}
