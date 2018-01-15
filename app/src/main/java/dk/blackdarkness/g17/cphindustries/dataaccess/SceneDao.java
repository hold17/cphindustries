package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Scene;

public interface SceneDao {
    List<Scene> getList();
    Scene getScene(int sceneId);
    void create(Scene scene);
    void update(Scene newScene);
    void delete(int sceneId);
}
