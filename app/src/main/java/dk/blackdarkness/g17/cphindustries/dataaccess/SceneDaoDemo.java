package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;
import dk.blackdarkness.g17.cphindustries.dto.Scene;

public class SceneDaoDemo implements SceneDao {
    private List<Scene> allScenes;

    @Override
    public List<Scene> get() {

        return DemoDataRepository.load();
    }

    @Override
    public Scene get(int id) {
        this.allScenes = DemoDataRepository.load();
        for (Scene s : this.allScenes) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void create(Scene scene) {
        this.allScenes = DemoDataRepository.load();
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
        this.allScenes = DemoDataRepository.load();
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
        for (Scene s : this.allScenes) {
            if (s.getId() == id) {
                this.allScenes.remove(s);
            }
        }

        DemoDataRepository.save(this.allScenes);
    }
}
