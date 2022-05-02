package spd.trello.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spd.trello.domain.Attachment;
import spd.trello.exception.FileAlreadyExistException;
import spd.trello.repository.AttachmentRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AttachmentService extends AbstractParentBasedService<Attachment, AttachmentRepository>{
    @Value("${save.tofile}")
    private boolean saveToFile;
    @Value("${save.path}")
    private String folder;

    protected AttachmentService(AttachmentRepository repository) {
        super(repository);
    }

    @SneakyThrows
    @Override
    public Attachment create(Attachment attachment) {
        Attachment saved = super.create(attachment);
        if (saveToFile)
        {
            File file = new File(folder+"/"+attachment.getName());
            System.out.println(file.getAbsolutePath());
            if (!file.createNewFile())
            {
                throw new FileAlreadyExistException();
            }
            OutputStream os = new FileOutputStream(file);
            os.write(attachment.getData().getFile());
            attachment.setLink(file.getAbsolutePath());
            attachment.getData().setFile(null);
        }

        return saved;

    }

    @Override
    @SneakyThrows
    public Attachment read(UUID id) {
        Attachment attachment = super.read(id);
        if (!attachment.getLink().isEmpty())
        {
            attachment.getData().setFile(Files.readAllBytes(Paths.get(attachment.getLink())));
        }
        return attachment;
    }
}
