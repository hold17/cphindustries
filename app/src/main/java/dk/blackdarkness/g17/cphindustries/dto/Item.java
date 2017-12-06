package dk.blackdarkness.g17.cphindustries.dto;

/**
 * Created by awo on 29/11/2017.
 */

public abstract class Item {
    protected int id;
    protected String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) { this.id = id; }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
