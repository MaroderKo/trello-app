package spd.trello.domain;

import lombok.Data;

import java.util.List;
@Data
public class CheckList {
    private String name;
    private List<CheckableItem> items;


    public CheckableItem getItem(int id)
    {
        return items.get(id);
    }

    public void addItem(CheckableItem item)
    {
        items.add(item);
    }

    public void removeItem(int id)
    {
        items.remove(id);
    }

}
