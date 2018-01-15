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
            if (sw.getShootId() == shootId && sw.getWeaponId() == weaponId) {
                return sw;
            }
        }

        return null;
    }

    @Override
    public void create(ShootWeapon shootWeapon) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        if (!exists(shootWeapon, this.allShootWeapon)) {
            shootWeapon.setShootWeaponId(1);

            for (ShootWeapon sw : allShootWeapon) {
                if (sw.getShootWeaponId() == shootWeapon.getShootWeaponId()) {
                    shootWeapon.setShootWeaponId(shootWeapon.getShootWeaponId() + 1);
                } else break;
            }
            this.allShootWeapon.add(shootWeapon);
        }

        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);
    }

    @Override
    public void update(int oldShootId, int oldWeaponId, ShootWeapon newShootWeapon) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        Boolean exist = false;
        Integer oldShootWeaponIndex = null;

        for (int i = 0; i < this.allShootWeapon.size(); i++) {
            final int currentShootId = this.allShootWeapon.get(i).getShootId();
            final int currentWeaponId = this.allShootWeapon.get(i).getWeaponId();

            // If the new ShootWeapon object exists
            if (currentShootId == newShootWeapon.getShootId() &&
                currentWeaponId == newShootWeapon.getWeaponId()) {
                    exist = true;
            }

            // If the current ShootWeapon is the same as the old ShootWeapon
            if(currentShootId == oldShootId &&
               currentWeaponId == oldWeaponId) {
                    oldShootWeaponIndex = i;
            }
        }

        if(oldShootWeaponIndex != null && exist) {
            this.allShootWeapon.remove(oldShootWeaponIndex.intValue());
        }

        if (!exist) {
            for (ShootWeapon sw : this.allShootWeapon) {
                if (sw.getShootId() == oldShootId) {
                    if (sw.getWeaponId() == oldWeaponId) {
                        sw.setShootId(newShootWeapon.getShootId());
                        sw.setWeaponId(newShootWeapon.getWeaponId());
                        break;
                    }
                }
            }
        }

        DemoDataRepository.saveListOfShootWeapon(allShootWeapon);
    }

    @Override
    public void delete(int shootId, int weaponId) {
        this.allShootWeapon = DemoDataRepository.loadListOfShootWeapon();

        for (ShootWeapon sw : this.allShootWeapon) {
            if (sw.getShootId() == shootId) {
                if (sw.getWeaponId() == weaponId) {
                    this.allShootWeapon.remove(sw);
                    break;
                }
            }
        }

        DemoDataRepository.saveListOfShootWeapon(this.allShootWeapon);
    }

    private static boolean exists(ShootWeapon shootWeapon, List<ShootWeapon> allShootWeapons) {
        for (ShootWeapon sw : allShootWeapons) {
            if (sw.getShootId() == shootWeapon.getShootId() &&
                    sw.getWeaponId() == shootWeapon.getWeaponId()) {
                    return true;
            }
        }

        return false;
    }
}
