package dk.blackdarkness.g17.cphindustries.dataaccess;

/**
 * Created by awo on 06-12-2017.
 */

public interface IDaoFactory {
    SceneDao getSceneDao();
    ShootDao getShootDao();
    WeaponDao getWeaponDao();
    ShootWeaponDao getShootWeaponDao();
}
