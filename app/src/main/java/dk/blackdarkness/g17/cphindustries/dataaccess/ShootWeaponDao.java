package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;

public interface ShootWeaponDao {
    List<ShootWeapon> getList();
    ShootWeapon getShootWeapon(int shootId, int weaponId);
    void create(ShootWeapon shootWeapon);
    void update(int oldShootId, int oldWeaponId, ShootWeapon updatedWeapon);
    void delete(int shootId, int weaponId);
}
