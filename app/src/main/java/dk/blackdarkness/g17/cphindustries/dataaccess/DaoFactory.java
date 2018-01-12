package dk.blackdarkness.g17.cphindustries.dataaccess;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class DaoFactory implements IDaoFactory {
    @Override
    public SceneDao getSceneDao() {
        if (!ApplicationConfig.useDemoData()) throw new NullPointerException("Demo data not enabled. SceneDao is null.");

        Log.d(TAG, "getSceneDao: Demo data enabled. Returning new SceneDaoDemo().");
        return new SceneDaoDemo(this);
    }

    @Override
    public ShootDao getShootDao() {
        if (!ApplicationConfig.useDemoData()) throw new NullPointerException("Demo data not enabled. ShootDao is null.");

        return new ShootDaoDemo(this);
    }

    @Override
    public WeaponDao getWeaponDao() {
        if (!ApplicationConfig.useDemoData()) throw new NullPointerException("Demo data not enabled. WeaponDao is null.");

        return new WeaponDaoDemo(this);
    }

    @Override
    public ShootWeaponDao getShootWeaponDao() {
        if (!ApplicationConfig.useDemoData()) throw new NullPointerException("Demo data not enabled. ShootWeaponDao is null.");

        return new ShootWeaponDaoDemo(this);
    }
}
