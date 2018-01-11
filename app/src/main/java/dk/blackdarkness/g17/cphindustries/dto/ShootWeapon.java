package dk.blackdarkness.g17.cphindustries.dto;

/**
 * Created by jonaslarsen on 09/01/2018.
 */

public class ShootWeapon {
    private int shootWeaponId;
    private int shootId;
    private int weaponId;

    public ShootWeapon(int shootWeaponId, int shootId, int weaponId) {
        this.shootWeaponId = shootWeaponId;
        this.shootId = shootId;
        this.weaponId = weaponId;
    }

    public int getShootWeaponId() {
        return shootWeaponId;
    }

    public void setShootWeaponId(int shootWeaponId) {
        this.shootWeaponId = shootWeaponId;
    }

    public int getShootId() {
        return shootId;
    }

    public void setShootId(int shootId) {
        this.shootId = shootId;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(int weaponId) {
        this.weaponId = weaponId;
    }
}
