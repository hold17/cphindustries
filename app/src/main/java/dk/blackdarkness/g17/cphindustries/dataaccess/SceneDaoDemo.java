package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

public class SceneDaoDemo implements SceneDao {
    private List<Scene> allScenes;
    private final IDaoFactory factory;


    public SceneDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Scene> get() {

        return DemoDataRepository.loadListOfScenes();
    }

    @Override
    public Scene get(int sceneId) {
        this.allScenes = DemoDataRepository.loadListOfScenes();
        for (Scene s : allScenes) {
            if (s.getId() == sceneId) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void create(Scene scene) {
        scene.setId(1);

        this.allScenes = DemoDataRepository.loadListOfScenes();

        // Finds new sceneId
        for (Scene s : allScenes) {
            if (s.getId() == scene.getId()) {
                scene.setId(s.getId() + 1);
            } else break;
        }

        allScenes.add(scene);
        DemoDataRepository.saveListOfScenes(allScenes);
    }

    @Override
    public void update(Scene updatedScene) {

        this.allScenes = DemoDataRepository.loadListOfScenes();
        for (Scene s : allScenes) {

            if (s.getId() == updatedScene.getId()) {
                s.setName(updatedScene.getName());
            }
        }
        DemoDataRepository.saveListOfScenes(allScenes);
    }

    @Override
    public void delete(int sceneId) {
        this.allScenes = DemoDataRepository.loadListOfScenes();

        // Find a scene and delete it.
        for (Scene s : allScenes) {
            if (s.getId() == sceneId) {
                allScenes.remove(s);
            }
        }

        List<Shoot> shoots = factory.getShootDao().get();

        for (Shoot shoot : shoots){
            if(shoot.getSceneId() == sceneId){
                factory.getShootDao().delete(shoot.getId());
            }
        }

        DemoDataRepository.saveListOfScenes(allScenes);
    }
}
