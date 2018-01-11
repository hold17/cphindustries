package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;

/**
 * Created by jonaslarsen on 10/01/2018.
 */

public class ShootWeaponDaoDemo implements ShootWeaponDao {

    private final IDaoFactory factory;
    private List<ShootWeapon> allShootWeapon;


    public ShootWeaponDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<ShootWeapon> get() {
        return DemoDataRepository.loadListOfShootWeapon();
    }

    @Override
    public ShootWeapon get(int shootWeaponId) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for (ShootWeapon sw : allShootWeapon){
            if (sw.getShootWeaponId()==shootWeaponId){
                return sw;
            }
        }

        return null;
    }

    @Override
    public void create(ShootWeapon shootWeapon) {
        shootWeapon.setShootWeaponId(1);

        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for (ShootWeapon sw :allShootWeapon){
            if (sw.getShootWeaponId() == shootWeapon.getShootWeaponId()) {
                shootWeapon.setShootWeaponId(shootWeapon.getShootWeaponId()+1);
            } else break;
        }

        this.allShootWeapon.add(shootWeapon);
        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);

    }

    @Override
    public void update(int shootWeaponId, ShootWeapon newWeapon) {

        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for (ShootWeapon sw : allShootWeapon){
            if (sw.getShootWeaponId()== shootWeaponId){
                sw.setShootWeaponId(shootWeaponId);
                sw.setShootId(newWeapon.getShootId());
                sw.setWeaponId(newWeapon.getWeaponId());
            }
        }
        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);

    }

    @Override
    public void delete(int shootWeaponId) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for(ShootWeapon sw : allShootWeapon){
            if (sw.getShootWeaponId()==shootWeaponId){
                allShootWeapon.remove(sw);
            }
        }

        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);

    }
}
