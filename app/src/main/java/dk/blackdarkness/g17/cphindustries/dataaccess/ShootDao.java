package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;

/**
 * Created by awo on 06-12-2017.
 */

public interface ShootDao {
    List<Shoot> get(int sceneId);
    Shoot get(int sceneId, int id);
    void create(int sceneId, Shoot shoot);
    void update(int sceneId, Shoot newShoot);
    void delete(int sceneId, int id);
}
