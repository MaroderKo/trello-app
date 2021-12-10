package spd.trello.domain;

import lombok.Data;

import java.util.List;
@Data
public class Board {
    private String name;
    private String description;
    private List<CardList> cardLists;
    private List<Member> members;
    private BoardVisibility visibility;
    private Boolean archived;

}
