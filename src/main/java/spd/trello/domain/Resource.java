package spd.trello.domain;


import lombok.*;

import java.time.LocalDateTime;
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Resource extends Domain{
    User createdBy;
    User updatedBy;
    final LocalDateTime createdDate = LocalDateTime.now();
    LocalDateTime updatedDate;

}
