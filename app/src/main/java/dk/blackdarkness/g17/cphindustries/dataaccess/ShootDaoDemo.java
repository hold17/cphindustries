package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

class ShootDaoDemo implements ShootDao {
    private final IDaoFactory factory;
    private List<Shoot> allShoots;

    ShootDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Shoot> getList() {
        return DemoDataRepository.loadListOfShoots();
    }

    public List<Shoot> getListByScene(int sceneID){
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
    public List<Weapon> getWeaponsByShoot(int shootId) {
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().getList();
        List<Weapon> weapons = new ArrayList<>();

        for (ShootWeapon sw : shootWeapons){
            if(sw.getShootId()==shootId){
                Weapon weapon = factory.getWeaponDao().getWeapon(sw.getWeaponId());
                weapons.add(weapon);
            }
        }
        return weapons;
    }

    @Override
    public Shoot getShoot(int shootId) {
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
        this.allShoots = DemoDataRepository.loadListOfShoots();
        shoot.setId(1);

        for (Shoot s : allShoots) {
            if (s.getId() == shoot.getId()) {
                shoot.setId(s.getId() + 1);
            } else break;
        }
        System.out.println("Created this shoot: " + shoot.toString());
        this.allShoots.add(shoot);
        DemoDataRepository.saveListOfShoots(allShoots);
    }

    @Override
    public void update(Shoot updatedShoot) {
        this.allShoots = DemoDataRepository.loadListOfShoots();
        int id = updatedShoot.getId();

        for (Shoot s : allShoots) {
            if (s.getId() == id) {
                s.setName(updatedShoot.getName());
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
        List<ShootWeapon> shootWeapons = factory.getShootWeaponDao().getList();

        for (ShootWeapon sw : shootWeapons){
            if(sw.getShootId() == shootId){
                factory.getShootWeaponDao().delete(sw.getShootWeaponId());
            }
        }
        DemoDataRepository.saveListOfShoots(allShoots);
    }
}
