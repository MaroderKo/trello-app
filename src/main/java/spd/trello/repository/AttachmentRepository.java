package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domain.Attachment;
@Repository
public interface AttachmentRepository extends AbstractParentBasedRepository<Attachment>{
}
