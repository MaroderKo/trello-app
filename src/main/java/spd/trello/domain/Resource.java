package spd.trello.domain;


import lombok.*;
import spd.trello.security.SecurityConfig;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@MappedSuperclass
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Resource extends Domain{
    String createdBy = "template";
    String updatedBy;
    LocalDateTime createdDate = LocalDateTime.now().withNano(0);
    LocalDateTime updatedDate;

}
