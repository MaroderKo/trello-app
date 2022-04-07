package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Attachment;
import spd.trello.repository.AttachmentRepository;

@Service
public class AttachmentService extends AbstractParentBasedService<Attachment, AttachmentRepository>{

    protected AttachmentService(AttachmentRepository repository) {
        super(repository);
    }



}
