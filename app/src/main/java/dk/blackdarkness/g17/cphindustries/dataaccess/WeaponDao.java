package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by awo on 06-12-2017.
 */

public interface WeaponDao {
    List<Weapon> get();
    Weapon get(int id);
    void create(Weapon weapon);
    void update(Weapon newWeapon);
    void delete(int id);
}
