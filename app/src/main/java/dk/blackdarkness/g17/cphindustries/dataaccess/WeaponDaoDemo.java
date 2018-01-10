package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Weapon;

public class WeaponDaoDemo implements WeaponDao {
    private final IDaoFactory factory;

    WeaponDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Weapon> get(int sceneId, int shootId) {
        return factory.getShootDao().get(sceneId, shootId).getWeapons();
    }

    @Override
    public Weapon get(int sceneId, int shootId, int id) {
        for (Weapon w : factory.getShootDao().get(sceneId, shootId).getWeapons()) {
            if (w.getId() == id) {
                return w;
            }
        }

        return null;
    }

    @Override
    public void create(int sceneId, int shootId, Weapon weapon) {
        weapon.setId(0);

        for (Weapon w : factory.getShootDao().get(sceneId, shootId).getWeapons()) {
            if (w.getId() > weapon.getId()) {
                weapon.setId(w.getId() + 1);
            }
        }

        factory.getShootDao().get(sceneId, shootId).getWeapons().add(weapon);
    }

    @Override
    public void update(int sceneId, int shootId, Weapon newWeapon) {
        for (Weapon w : factory.getShootDao().get(sceneId, shootId).getWeapons()) {
            if (w.getId() == newWeapon.getId()) {
                w.setConnectionStatus(newWeapon.getConnectionStatus());
                w.setFireMode(newWeapon.getFireMode());
                w.setIp(newWeapon.getIp());
                w.setMac(newWeapon.getMac());
                w.setWarnings(newWeapon.getWarnings());
                w.setName(newWeapon.getName());
            }
        }
    }

    @Override
    public void delete(int sceneId, int shootId, int id) {
        for (Weapon w : factory.getShootDao().get(sceneId, shootId).getWeapons()) {
            if (w.getId() == id) {
                factory.getShootDao().get(sceneId, shootId).getWeapons().remove(w);
            }
        }
    }
}
