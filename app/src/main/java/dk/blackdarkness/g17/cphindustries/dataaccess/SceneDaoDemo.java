package dk.blackdarkness.g17.cphindustries.dataaccess;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;



/**
 * Created by awo on 06-12-2017.
 */

public class SceneDaoDemo implements SceneDao {

    private List<Scene> allScenes;

    @Override
    public List<Scene> get() {

        return DemoData.load();
    }

    @Override
    public Scene get(int id) {
        this.allScenes = DemoData.load();
        for (Scene s : this.allScenes) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void create(Scene scene) {
        this.allScenes = DemoData.load();
        for (Scene s : this.allScenes) {
            if (s.getId() > scene.getId()) {
                scene.setId(s.getId() + 1);
            }
        }

        this.allScenes.add(scene);
        DemoData.save(this.allScenes);

    }

    @Override
    public void update(Scene updatedScene) {
        this.allScenes = DemoData.load();
        for (Scene s : this.allScenes) {
            if (s.getId() == updatedScene.getId()) {
                s.setShoots(updatedScene.getShoots());
                s.setName(updatedScene.getName());
            }
        }
        DemoData.save(this.allScenes);

    }

    @Override
    public void delete(int id) {
        for (Scene s : this.allScenes) {
            if (s.getId() == id) {
                this.allScenes.remove(s);
            }
        }
        DemoData.save(this.allScenes);

    }

}
