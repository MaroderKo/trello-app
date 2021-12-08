import java.time.LocalDateTime;
import java.util.List;

public class Card {
    private String name;
    private String description;
    private List<Member> assignedMembers;
    private List<Label> labels;
    private List<Attachment> attachments;
    private boolean isArchived;
    private List<Comment> comments;
    private Reminder reminder;
    private List<CheckList> checkLists;
    private LocalDateTime creationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public Comment getComment(int id) {
        return comments.get(id);
    }

    public void removeComment(int id) {
        comments.remove(id);
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public Label getLabel(int id) {
        return labels.get(id);
    }

    public void removeLabel(int id) {
        labels.remove(id);
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public void removeAttachment(int id) {
        attachments.remove(id);
    }

    public Attachment getAttachment(int id) {
        return attachments.get(id);
    }

    public void addMember(Member member) {
        assignedMembers.add(member);
    }

    public void removeMember(int id) {
        assignedMembers.remove(id);
    }

    public Member getMember(int id) {
        return assignedMembers.get(id);
    }

    public void addCheckList(CheckList checkList) {
        checkLists.add(checkList);
    }

    public void removeCheckList(int id) {
        checkLists.remove(id);
    }

    public CheckList getCheckList(int id) {
        return checkLists.get(id);
    }


}
