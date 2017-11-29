package dk.blackdarkness.g17.cphindustries.dto;

import java.util.List;

/**
 * Created by awo on 29/11/2017.
 */

public class Scene  extends Item {
    private List<Shoot> shoots;

    public Scene(int id, String name, List<Shoot> shoots) {
        super(id, name);
        this.shoots = shoots;
    }

    public List<Shoot> getShoots() {
        return shoots;
    }

    public void setShoots(List<Shoot> shoots) {
        this.shoots = shoots;
    }
}
