package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

/**
 * Created by awo on 06-12-2017.
 */

public class ShootDaoDemo implements ShootDao {
    private final IDaoFactory factory;

    ShootDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Shoot> get(int sceneId) {
        return factory.getSceneDao().get(sceneId).getShoots();
    }

    @Override
    public Shoot get(int sceneId, int id) {
        for (Shoot s : factory.getSceneDao().get(sceneId).getShoots()) {
            if (s.getId() == id) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void create(int sceneId, Shoot newShoot) {
        final SceneDaoDemo dao = new SceneDaoDemo();
        final Scene scene = dao.get(sceneId);

        int highestId = newShoot.getId();

        for (Shoot shoot : scene.getShoots()) {
            // +1 for O(n^2)
            if (shoot.getId() > highestId) {
                highestId = shoot.getId() + 1;
            }
        }

        newShoot.setId(highestId);

        List<Shoot> sceneShoots = scene.getShoots();
        sceneShoots.add(newShoot);
        scene.setShoots(sceneShoots);

        dao.update(scene);
    }

    @Override
    public void update(int sceneId, Shoot newShoot) {
        for (Shoot s : factory.getSceneDao().get(sceneId).getShoots()) {
            if (s.getId() == newShoot.getId()) {
                s.setName(newShoot.getName());
                s.setWeapons(newShoot.getWeapons());
            }
        }
    }

    @Override
    public void delete(int sceneId, int id) {
        for (Shoot s : factory.getSceneDao().get(sceneId).getShoots()) {
            if (s.getId() == id) {
                factory.getSceneDao().get(sceneId).getShoots().remove(s);
            }
        }
    }
}
