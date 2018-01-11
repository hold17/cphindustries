package dk.blackdarkness.g17.cphindustries.dto;

import java.util.ArrayList;
import java.util.List;

public class Shoot extends Item {
    private int sceneId;

    public Shoot(int shootId, String name, int sceneId) {
        super(shootId, name);
        this.sceneId = sceneId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }
}
