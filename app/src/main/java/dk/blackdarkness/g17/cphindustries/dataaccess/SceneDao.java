package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Scene;

/**
 * Created by awo on 06-12-2017.
 */

public interface SceneDao {
    List<Scene> get();
    Scene get(int id);
    void create(Scene scene);
    void update(Scene newScene);
    void delete(int id);
}
