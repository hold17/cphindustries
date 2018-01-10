package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by jonaslarsen on 06-12-2017.
 */

public class ShootDaoDemo implements ShootDao {
    private final IDaoFactory factory;
    private List<Shoot> allShoots;

    ShootDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Shoot> get() {
        return DemoDataRepository.loadListOfShoots();
    }

    public List<Shoot> getShoots(int sceneID){

        this.allShoots = DemoDataRepository.loadListOfShoots();

        List<Shoot> shoots = new ArrayList<>();

        for (Shoot s : this.allShoots){
            if (s.getSceneId()==sceneID){
                shoots.add(s);
            }
        }
        return shoots;
    }

    @Override
    public List<Weapon> getWeapons(int shootId) {
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().get();
        List<Weapon> weapons = new ArrayList<>();

        for (ShootWeapon sw : shootWeapons){
            if(sw.getShootId()==shootId){
                Weapon weapon = factory.getWeaponDao().get(sw.getWeaponId());
                weapons.add(weapon);
            }
        }

        return weapons;
    }

    @Override
    public Shoot get(int shootId) {

       this.allShoots = DemoDataRepository.loadListOfShoots();

        for (Shoot s : allShoots) {
            if (s.getId() == shootId) {
                return s;
            }
        }

        return null;
    }

    @Override
    public void create(Shoot shoot) {
        shoot.setId(0);

        this.allShoots = DemoDataRepository.loadListOfShoots();

        for (Shoot s : allShoots) {
            if (s.getId() > shoot.getId()) {
                shoot.setId(s.getId() + 1);
            }
        }

        DemoDataRepository.saveListOfShoots(allShoots);
    }

    @Override
    public void update(int shootId, Shoot newShoot) {
        this.allShoots = DemoDataRepository.loadListOfShoots();

        for (Shoot s : allShoots) {
            if (s.getId() == shootId) {
                s.setName(newShoot.getName());
            }
        }
        DemoDataRepository.saveListOfShoots(allShoots);
    }

    @Override
    public void delete(int shootId) {
        this.allShoots = DemoDataRepository.loadListOfShoots();

        for (Shoot s : allShoots) {
            if (s.getId() == shootId) {
                allShoots.remove(s);
            }
        }

        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().get();

        for (ShootWeapon sw : shootWeapons){
            if(sw.getShootId() == shootId){
                factory.getShootWeaponDao().delete(sw.getShootWeaponId());
            }
        }

        DemoDataRepository.saveListOfShoots(allShoots);
    }
}
