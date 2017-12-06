package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by awo on 06-12-2017.
 */

public class WeaponDaoDemo implements WeaponDao {
    @Override
    public List<Weapon> get() {
        return new ArrayList<>();
    }

    @Override
    public Weapon get(int id) {
        return null;
    }

    @Override
    public void create(Weapon weapon) {

    }

    @Override
    public void update(Weapon newWeapon) {

    }

    @Override
    public void delete(int id) {

    }
}
