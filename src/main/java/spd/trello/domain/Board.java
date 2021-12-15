package spd.trello.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Board extends Resource{
    private String name;
    private String description;
    private List<CardList> cardLists = new ArrayList<>();
    private List<Member> members;
    private BoardVisibility visibility;
    private Boolean archived;

}
