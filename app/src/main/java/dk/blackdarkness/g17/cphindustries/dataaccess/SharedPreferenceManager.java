package dk.blackdarkness.g17.cphindustries.dataaccess;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class SharedPreferenceManager {
    private final SharedPreferences prefs;
    private final Context context;
    private final Gson gson;

    private static volatile SharedPreferenceManager instance;

    /**
     * A context is needed in order to save in Shared Preferences
     * @param context
     */
    private SharedPreferenceManager(Context context) {
        this.context = context.getApplicationContext();
        this.gson = new Gson();

        this.prefs = this.context.getSharedPreferences("GlobalState", Context.MODE_PRIVATE);
    }

    /**
     * Coded as a singleton. The singleton might be cleaned up by android, but does not matter too much since a global application context is used
     * @return Returns the current initialized instance
     */
    public static SharedPreferenceManager getInstance() {
        if (instance == null) return null;

        return instance;
    }

    /**
     * @param context Some context, does not matter which
     */
    public static void init(Context context) {
        if (instance == null) {
            synchronized (SharedPreferenceManager.class) {
                if (instance == null) {
                    instance = new SharedPreferenceManager(context);
                }
            }
        }
    }

    /**
     * Saves objects in Shared Preferences
     * @param storeLocation Where to store the object - should be a string from "strings.xml"
     * @param object The object to be stored
     */
    public void saveObject(String storeLocation, Object object) {
        if (instance == null) return;

        final String jsonObj = gson.toJson(object);

        this.prefs.edit().putString(storeLocation, jsonObj).apply();
    }

    /**
     * Retrieves an object from Shared Preferences
     * @param storeLocation Where the object is stored - should be a string from "strings.xml"
     * @return Returns the wanted object if it exists, null if it does not
     */
    public Object getObject(String storeLocation, Type returnType) {
        if (instance == null) return null;

        final String jsonObj = this.prefs.getString(storeLocation, null);

        final Object obj = this.gson.fromJson(jsonObj, returnType);

        return obj;
    }

    /**
     * Clears value stored at a specific location
     * @param storeLocation What to delete
     */
    public void clear(String storeLocation) {
        this.prefs.edit().remove(storeLocation).apply();
    }

    /**
     * WARNING: This clears everything stored in SharedPreferences. Use with caution...
     */
    public void clear() {
        this.prefs.edit().clear().apply();
    }
}
