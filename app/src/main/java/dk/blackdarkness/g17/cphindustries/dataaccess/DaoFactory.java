package dk.blackdarkness.g17.cphindustries.dataaccess;

/**
 * Created by awo on 06-12-2017.
 */

public class DaoFactory implements IDaoFactory {
    @Override
    public SceneDao getSceneDao() {
        if (!ApplicationConfig.useDemoData()) return null;

        return new SceneDaoDemo();
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
}
