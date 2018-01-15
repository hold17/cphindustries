package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

public interface WeaponDao {
    List<Weapon> getList();
    Weapon getWeapon(int weaponId);
    void create(Weapon weapon);
    void update(Weapon newWeapon);
    void delete(int weaponId);
    List<Weapon> getListByShoot(int shootId);
    List<Shoot> getShootsByWeapon(int weaponId);
}
