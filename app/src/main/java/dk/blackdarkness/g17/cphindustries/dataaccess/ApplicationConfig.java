package dk.blackdarkness.g17.cphindustries.dataaccess;

import android.app.Application;

/**
 * Created by awo on 06-12-2017.
 */

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
        return true; // TODO: Read from shared prefs or something...
    }

    public static DaoFactory getDaoFactory() {
        return new DaoFactory();
    }
}
