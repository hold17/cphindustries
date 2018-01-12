package dk.blackdarkness.g17.cphindustries.dataaccess;

import android.app.Application;

public class ApplicationConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Decides whether or not hardcoded data is used or not
     * @return true if demo data is used, false if data should be fetched from api or cache
     */
    public static boolean useDemoData() {
        if (SharedPreferenceManager.getInstance() == null)
            throw new NullPointerException("SharedPreferenceManager not initialized.");

        boolean useDemoData = SharedPreferenceManager.getInstance().getBoolean("demoDataSwitch");

        if (useDemoData)
            return true; // TODO: Read from shared prefs or something...
        else {
            return false;
        }

    }

    public static DaoFactory getDaoFactory() {
        return new DaoFactory();
    }
}
