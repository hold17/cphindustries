package dk.blackdarkness.g17.cphindustries.dataaccess;

public class DaoFactory implements IDaoFactory {
    @Override
    public SceneDao getSceneDao() {
        if (!ApplicationConfig.useDemoData()) return null;

        return new SceneDaoDemo(this);
    }

    @Override
    public ShootDao getShootDao() {
        if (!ApplicationConfig.useDemoData()) return null;

        return new ShootDaoDemo(this);
    }

    @Override
    public WeaponDao getWeaponDao() {
        if (!ApplicationConfig.useDemoData()) return null;

        return new WeaponDaoDemo(this);
    }

    @Override
    public ShootWeaponDao getShootWeaponDao() {
        if (!ApplicationConfig.useDemoData()) return null;

        return new ShootWeaponDaoDemo(this);
    }
}
