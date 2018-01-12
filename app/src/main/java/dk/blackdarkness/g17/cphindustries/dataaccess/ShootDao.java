package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

public interface ShootDao {
    List<Shoot> getList();
    Shoot getShoot(int shootId);
    void create( Shoot shoot);
    void update(int shootId, Shoot newShoot);
    void delete(int shootId);
    List<Shoot> getListByScene(int sceneId);
    List<Weapon> getWeaponsByShoot(int shootId);
}
