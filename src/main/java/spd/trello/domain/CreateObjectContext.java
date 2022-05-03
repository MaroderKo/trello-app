package spd.trello.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@Component
public class CreateObjectContext {
    UUID parentId;
}
