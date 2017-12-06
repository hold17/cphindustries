package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;

/**
 * Created by awo on 06-12-2017.
 */

public class ShootDaoDemo implements ShootDao {
    @Override
    public List<Shoot> get() {
        return new ArrayList<>();
    }

    @Override
    public Shoot get(int id) {
        return null;
    }

    @Override
    public void create(Shoot shoot) {

    }

    @Override
    public void update(Shoot newShoot) {

    }

    @Override
    public void delete(int id) {

    }
}
