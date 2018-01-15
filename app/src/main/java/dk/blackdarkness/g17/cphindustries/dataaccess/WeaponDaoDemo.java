package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

class WeaponDaoDemo implements WeaponDao {
    private List<Weapon> allWeapons;
    private final IDaoFactory factory;

    WeaponDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Weapon> getList() {
        return DemoDataRepository.loadListOfWeapons();
    }

    @Override
    public Weapon getWeapon(int weaponId) {
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
        this.allWeapons = DemoDataRepository.loadListOfWeapons();
        weapon.setId(1);

        for (Weapon w : allWeapons) {
            if (w.getId() == weapon.getId()) {
                weapon.setId(w.getId() + 1);
            } else break;
        }
        this.allWeapons.add(weapon);
        DemoDataRepository.saveListOfWeapons(allWeapons);
    }

    @Override
    public void update(Weapon updatedWeapon) {
        this.allWeapons = DemoDataRepository.loadListOfWeapons();
        int id = updatedWeapon.getId();

        for (Weapon w : allWeapons) {
            if (w.getId() == id) {
                w.setConnectionStatus(updatedWeapon.getConnectionStatus());
                w.setFireMode(updatedWeapon.getFireMode());
                w.setIp(updatedWeapon.getIp());
                w.setMac(updatedWeapon.getMac());
                w.setWarnings(updatedWeapon.getWarnings());
                w.setName(updatedWeapon.getName());
                break;
            }
        }
        DemoDataRepository.saveListOfWeapons(allWeapons);
    }

    @Override
    public void delete(int weaponId) {
        this.allWeapons = DemoDataRepository.loadListOfWeapons();

        for (Weapon w : this.allWeapons) {
            if (w.getId() == weaponId) {
                this.allWeapons.remove(w);
                break;
            }
        }
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().getList();

        for (ShootWeapon sw : shootWeapons) {
            if (sw.getWeaponId() == weaponId) {
                factory.getShootWeaponDao().delete(sw.getShootId(),sw.getShootWeaponId());
            }
        }
        DemoDataRepository.saveListOfWeapons(allWeapons);
    }

    @Override
    public List<Shoot> getShootsByWeapon(int weaponId) {
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().getList();
        List<Shoot> shoots = new ArrayList<>();

        for (ShootWeapon sw : shootWeapons){
            if (sw.getWeaponId()==weaponId){
                Shoot shoot = factory.getShootDao().getShoot(sw.getShootId());
                shoots.add(shoot);
            }
        }
        return shoots;
    }

    @Override
    public List<Weapon> getListByShoot(int shootId) {
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().getList();
        List<Weapon> weapons = new ArrayList<>();

        for (ShootWeapon sw : shootWeapons){
            if (sw.getShootId()==shootId){
                Weapon weapon = factory.getWeaponDao().getWeapon(sw.getWeaponId());
                weapons.add(weapon);
            }
        }
        return weapons;
    }
}
