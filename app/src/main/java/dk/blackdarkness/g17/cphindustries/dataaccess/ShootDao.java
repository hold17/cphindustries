package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;

/**
 * Created by awo on 06-12-2017.
 */

public interface ShootDao {
    List<Shoot> get();
    Shoot get(int id);
    void create(Shoot shoot);
    void update(Shoot newShoot);
    void delete(int id);
}
