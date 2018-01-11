package dk.blackdarkness.g17.cphindustries.dataaccess;

interface IDaoFactory {
    SceneDao getSceneDao();
    ShootDao getShootDao();
    WeaponDao getWeaponDao();
    ShootWeaponDao getShootWeaponDao();
}
