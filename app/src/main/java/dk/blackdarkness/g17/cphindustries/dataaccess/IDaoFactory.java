package dk.blackdarkness.g17.cphindustries.dataaccess;

public interface IDaoFactory {
    SceneDao getSceneDao();
    ShootDao getShootDao();
    WeaponDao getWeaponDao();
    ShootWeaponDao getShootWeaponDao();
}
