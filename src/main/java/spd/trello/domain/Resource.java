package spd.trello.domain;


import lombok.*;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@MappedSuperclass
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Resource extends Domain{
    String createdBy = "";
    String updatedBy;
    LocalDateTime createdDate = LocalDateTime.now();
    LocalDateTime updatedDate;

}
