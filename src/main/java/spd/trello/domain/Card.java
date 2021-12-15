package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Card extends Resource{
    private String name;
    private String description;
    private List<Member> assignedMembers;
    private List<Label> labels;
    private List<Attachment> attachments;
    private Boolean archived;
    private List<Comment> comments;
    private Reminder reminder;
    private List<CheckList> checkLists;

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", assignedMembers=" + assignedMembers +
                ", labels=" + labels +
                ", attachments=" + attachments +
                ", archived=" + archived +
                ", comments=" + comments +
                ", reminder=" + reminder +
                ", checkLists=" + checkLists +
                ", id=" + id +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
