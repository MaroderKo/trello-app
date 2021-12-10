package spd.trello.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class Card {
    private String name;
    private String description;
    private List<Member> assignedMembers;
    private List<Label> labels;
    private List<Attachment> attachments;
    private Boolean archived;
    private List<Comment> comments;
    private Reminder reminder;
    private List<CheckList> checkLists;
    private LocalDateTime creationDate;



}
