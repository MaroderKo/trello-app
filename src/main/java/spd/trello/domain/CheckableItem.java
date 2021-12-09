package spd.trello.domain;

import lombok.Data;

@Data
public class CheckableItem {
    private String name;
    private boolean checked;

}
