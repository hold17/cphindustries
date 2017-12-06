package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;
import java.util.ArrayList;

import dk.blackdarkness.g17.cphindustries.dto.Scene;

/**
 * Created by awo on 06-12-2017.
 */

public class SceneDaoDemo implements SceneDao {
    @Override
    public List<Scene> get() {
        return new ArrayList<>();
    }

    @Override
    public Scene get(int id) {
        return null;
    }

    @Override
    public void create(Scene scene) {

    }

    @Override
    public void update(Scene newScene) {

    }

    @Override
    public void delete(int id) {

    }
}
