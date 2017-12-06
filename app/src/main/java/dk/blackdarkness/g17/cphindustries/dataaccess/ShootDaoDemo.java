package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.ArrayList;
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
    public void create(int sceneId, Shoot shoot) {
        shoot.setId(0);

        for (Shoot s : factory.getSceneDao().get(sceneId).getShoots()) {
            if (s.getId() > shoot.getId()) {
                shoot.setId(s.getId() + 1);
            }
        }

        factory.getSceneDao().get(sceneId).getShoots().add(shoot);
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
