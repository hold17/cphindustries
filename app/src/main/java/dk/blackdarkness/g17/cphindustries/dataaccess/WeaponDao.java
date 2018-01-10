package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by awo on 06-12-2017.
 */

public interface WeaponDao {
    List<Weapon> get();
    Weapon get(int weaponId);
    void create(Weapon weapon);
    void update(int weaponId, Weapon newWeapon);
    void delete(int weaponId);
    List<Shoot> getShoots(int weaponId);
    List<Weapon> getWeapons(int shootId);
}
