package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by awo on 06-12-2017.
 */

public interface WeaponDao {
    List<Weapon> get(int sceneId, int shootId);
    Weapon get(int sceneId, int shootId, int id);
    void create(int sceneId, int shootId, Weapon weapon);
    void update(int sceneId, int shootId, Weapon newWeapon);
    void delete(int sceneId, int shootId, int id);
}
