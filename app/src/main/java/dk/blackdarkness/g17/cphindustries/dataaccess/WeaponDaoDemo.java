package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by jonaslarsen on 06-12-2017.
 */

public class WeaponDaoDemo implements WeaponDao {
    private List<Weapon> allWeapons;

    private final IDaoFactory factory;

    WeaponDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Weapon> get() {
        return DemoDataRepository.loadListOfWeapons();
    }

    @Override
    public Weapon get(int weaponId) {

        this.allWeapons = DemoDataRepository.loadListOfWeapons();

        for (Weapon w : allWeapons) {
            if (w.getId() == weaponId) {
                return w;
            }
        }

        return null;
    }

    @Override
    public void create(Weapon weapon) {
        weapon.setId(1);

        this.allWeapons = DemoDataRepository.loadListOfWeapons();

        for (Weapon w : allWeapons) {
            if (w.getId() == weapon.getId()) {
                weapon.setId(w.getId() + 1);
            } else break;
        }

        this.allWeapons.add(weapon);
        DemoDataRepository.saveListOfWeapons(allWeapons);
    }

    @Override
    public void update(int weaponId, Weapon newWeapon) {

        this.allWeapons = DemoDataRepository.loadListOfWeapons();

        for (Weapon w : allWeapons) {
            if (w.getId() == newWeapon.getId()) {
                w.setConnectionStatus(newWeapon.getConnectionStatus());
                w.setFireMode(newWeapon.getFireMode());
                w.setIp(newWeapon.getIp());
                w.setMac(newWeapon.getMac());
                w.setWarnings(newWeapon.getWarnings());
                w.setName(newWeapon.getName());
            }
        }
        DemoDataRepository.saveListOfWeapons(allWeapons);
    }

    @Override
    public void delete(int weaponId) {
//
        this.allWeapons = DemoDataRepository.loadListOfWeapons();

        for (Weapon w : allWeapons) {
            if (w.getId() == weaponId) {
                allWeapons.remove(w);
            }
        }

        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().get();

        for (ShootWeapon sw : shootWeapons) {
            if (sw.getWeaponId() == weaponId) {
                factory.getShootWeaponDao().delete(sw.getShootWeaponId());
            }
        }

        DemoDataRepository.saveListOfWeapons(allWeapons);
    }

    @Override
    public List<Shoot> getShoots(int weaponId) {
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().get();
        List<Shoot> shoots = new ArrayList<>();

        for (ShootWeapon sw : shootWeapons){
            if (sw.getWeaponId()==weaponId){
                Shoot shoot = factory.getShootDao().get(sw.getShootId());
                shoots.add(shoot);
            }
        }

        return shoots;
    }

    @Override
    public List<Weapon> getWeapons(int shootId) {
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().get();
        List<Weapon> weapons = new ArrayList<>();

        for (ShootWeapon sw : shootWeapons){
            if (sw.getShootId()==shootId){
                Weapon weapon = factory.getWeaponDao().get(sw.getWeaponId());
                weapons.add(weapon);
            }
        }
        return weapons;
    }
}
