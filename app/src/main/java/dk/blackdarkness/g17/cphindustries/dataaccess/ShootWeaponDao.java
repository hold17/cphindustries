package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;

public interface ShootWeaponDao {
    List<ShootWeapon> getList();
    ShootWeapon getShootWeapon(int shootWeaponId);
    void create(ShootWeapon shootWeapon);
    void update(int shootWeaponId, ShootWeapon newWeapon);
    void delete(int shootWeaponId);
}
