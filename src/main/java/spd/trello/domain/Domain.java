package spd.trello.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Domain {
    @Id
    UUID id = UUID.randomUUID();
}
