package dk.blackdarkness.g17.cphindustries.dataaccess;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;

class ShootWeaponDaoDemo implements ShootWeaponDao {
    private final IDaoFactory factory;
    private List<ShootWeapon> allShootWeapon;

    ShootWeaponDaoDemo(IDaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<ShootWeapon> getList() {
        return DemoDataRepository.loadListOfShootWeapon();
    }

    @Override
    public ShootWeapon getShootWeapon(int shootId, int weaponId) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for (ShootWeapon sw : allShootWeapon) {
            if (sw.getShootId() == shootId) {
                if (sw.getWeaponId() == weaponId) {
                    return sw;
                }
            }
        }
        return null;
    }

    @Override
    public void create(ShootWeapon shootWeapon) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();
        shootWeapon.setShootWeaponId(1);

        for (ShootWeapon sw : allShootWeapon) {
            if (sw.getShootWeaponId() == shootWeapon.getShootWeaponId()) {
                shootWeapon.setShootWeaponId(shootWeapon.getShootWeaponId() + 1);
            } else break;
        }
        this.allShootWeapon.add(shootWeapon);
        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);
    }

    @Override
    public void update(int shootId, int weaponId, ShootWeapon newWeapon) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for (ShootWeapon sw : allShootWeapon) {
            if (sw.getShootId() == shootId) {
                if (sw.getWeaponId() == weaponId) {
                    sw.setShootId(shootId);
                    sw.setWeaponId(weaponId);
                }
            }
        }
        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);
    }

    @Override
    public void delete(int shootId, int weaponId) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for (ShootWeapon sw : allShootWeapon) {
            if (sw.getShootId() == shootId) {
                if (sw.getWeaponId() == weaponId) {
                    allShootWeapon.remove(sw);
                }
            }
        }
        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);
    }
}
