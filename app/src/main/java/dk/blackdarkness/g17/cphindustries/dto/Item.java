package dk.blackdarkness.g17.cphindustries.dto;

public abstract class Item {
    protected int id;
    protected String name;

    public Item(){

    }

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
