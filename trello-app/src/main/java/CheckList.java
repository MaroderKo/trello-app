import java.util.List;

public class CheckList {
    private String name;
    private List<CheckableItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
