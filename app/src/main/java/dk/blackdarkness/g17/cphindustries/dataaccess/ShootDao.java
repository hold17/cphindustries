package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

public interface ShootDao {
    List<Shoot> get();
    Shoot get(int shootId);
    void create( Shoot shoot);
    void update(int shootId, Shoot newShoot);
    void delete(int shootId);
    List<Shoot> getShoots(int sceneId);
    List<Weapon> getWeapons(int shootId);
}
