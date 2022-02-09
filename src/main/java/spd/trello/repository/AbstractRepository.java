package spd.trello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import spd.trello.domain.Domain;

import java.util.UUID;

@NoRepositoryBean
public interface AbstractRepository<T extends Domain> extends JpaRepository<T, UUID> {
    default T update(T t) {
        return save(t);
    }
}
