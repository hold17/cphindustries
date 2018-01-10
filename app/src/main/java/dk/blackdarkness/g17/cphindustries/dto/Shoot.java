package dk.blackdarkness.g17.cphindustries.dto;

import java.util.ArrayList;
import java.util.List;

public class Shoot extends Item {
    private List<Weapon> weapons;

    public Shoot(int id, String name) {
        super(id, name);
        this.weapons = new ArrayList<>();
    }

    public Shoot(int id, String name, List<Weapon> weapons) {
        super(id, name);
        this.weapons = weapons;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }
}
