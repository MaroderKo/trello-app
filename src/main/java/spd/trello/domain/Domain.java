package spd.trello.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Domain {
    @Hidden
    @Id
    UUID id = UUID.randomUUID();
}
