import java.time.LocalDateTime;
import java.util.List;

public class Comment {
    private Member member;
    private String text;
    private LocalDateTime date;
    private List<Attachment> attachments;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

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

