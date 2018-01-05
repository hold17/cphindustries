package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;
import dk.blackdarkness.g17.cphindustries.dto.Scene;

/**
 * Created by awo on 06-12-2017.
 */

public class SceneDaoDemo implements SceneDao {
    private List<Scene> allScenes;

    public SceneDaoDemo() {
        this.allScenes = DemoDataRepository.load();
    }

    @Override
    public List<Scene> get() {

        return DemoDataRepository.load();
    }

    @Override
    public Scene get(int id) {
        for (Scene s : this.allScenes) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void create(Scene scene) {
        for (Scene s : this.allScenes) {
            if (s.getId() > scene.getId()) {
                scene.setId(s.getId() + 1);
            }
        }

        this.allScenes.add(scene);
        DemoDataRepository.save(this.allScenes);
    }

    @Override
    public void update(Scene updatedScene) {
        for (Scene s : this.allScenes) {
            if (s.getId() == updatedScene.getId()) {
                s.setShoots(updatedScene.getShoots());
                s.setName(updatedScene.getName());
            }
        }

        DemoDataRepository.save(this.allScenes);
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < this.allScenes.size(); i++)
            if (this.allScenes.get(i).getId() == id) {
                this.allScenes.remove(i);
            }

        DemoDataRepository.save(this.allScenes);
    }
}
