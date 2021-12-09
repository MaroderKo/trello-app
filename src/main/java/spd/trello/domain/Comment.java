package spd.trello.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class Comment {
    private Member member;
    private String text;
    private LocalDateTime date;
    private List<Attachment> attachments;


    public void addAttachment(Attachment attachment)
    {
        attachments.add(attachment);
    }

    public void removeAttachment(int id)
    {
        attachments.remove(id);
    }

    public Attachment getAttachment(int id)
    {
        return attachments.get(id);
    }

}

