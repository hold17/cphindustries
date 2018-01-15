package dk.blackdarkness.g17.cphindustries.menuitems;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import dk.blackdarkness.g17.cphindustries.BuildConfig;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat implements android.support.v7.preference.Preference.OnPreferenceClickListener {
    private static final String TAG = "SettingsFragment";
    public static final String CACHE_CLEARED = "cacheCleared";
    private static final String KEY_PREF_CLEAR_SETTINGS = "clearSettings";
    private static final String KEY_PREF_CLEAR_CACHE = "clearCache";
    private static final String KEY_PREF_DEMO_DATA = "demoDataSwitch";
    private static final String KEY_PREF_ORIENTATION = "orientationSwitch";
    private static final String KEY_PREF_UPDATE_RATE= "updateRate";
    private static final String KEY_PREF_BUILD_NUMBER = "buildNumber";
    private static final String SAVED_SCENES_LOCATION = "SAVED_SCENES_LIST";
    private static final String SAVED_SHOOTS_LOCATION = "SAVED_SHOOTS_LIST";
    private static final String SAVED_WEAPONS_LOCATION = "SAVED_WEAPONS_LIST";
    private static final String SAVED_SHOOTWEAPON_LOCATION = "SAVED_SHOOTWEAPON_LIST";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        android.support.v7.preference.Preference clearSettings = findPreference(KEY_PREF_CLEAR_SETTINGS);
        clearSettings.setOnPreferenceClickListener(this);

        android.support.v7.preference.Preference clearCache = findPreference(KEY_PREF_CLEAR_CACHE);
        clearCache.setOnPreferenceClickListener(this);
        android.support.v7.preference.Preference buildNumber = findPreference(KEY_PREF_BUILD_NUMBER);
        buildNumber.setSummary(BuildConfig.VERSION_NAME);


        /****************************************************
        BELOW CODE IN onCreate() IS FOR TESTING PURPOSES ONLY
        ****************************************************/

        SharedPreferenceManager sharedPref = SharedPreferenceManager.getInstance();

        //Test of switches, logs booleans onCreate
        Boolean demoPref, orientationPref;
        demoPref = sharedPref.getBoolean(KEY_PREF_DEMO_DATA);
        orientationPref = sharedPref.getBoolean(KEY_PREF_ORIENTATION);
        Log.d(TAG, "onCreatePreferences: demoPref: " + demoPref.toString() + "\n" + "orientationPref: " + orientationPref.toString());

        //Test of listPref, logs selected value onCreate
        String value = sharedPref.getString(KEY_PREF_UPDATE_RATE);
        Log.d(TAG, "onCreatePreferences: updateRate: " + value);
    }

    @Override
    public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {

        //Test preferences, creates toast onClick. Implement methods here.
        switch(preference.getKey()) {
            case KEY_PREF_CLEAR_SETTINGS:
                AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
                a_builder.setMessage("Resetting the application settings will reset.... Do you still want to....")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //TODO: Clear settings here. We need to find out exactly what settings to clear!
                                Toast.makeText(getContext(), "Settings reset", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "Reset cancelled", Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Warning");
                alert.show();
                return true;
            case KEY_PREF_CLEAR_CACHE:
                SharedPreferenceManager.getInstance().clear(SAVED_SCENES_LOCATION);
                SharedPreferenceManager.getInstance().clear(SAVED_SHOOTS_LOCATION);
                SharedPreferenceManager.getInstance().clear(SAVED_WEAPONS_LOCATION);
                SharedPreferenceManager.getInstance().clear(SAVED_SHOOTWEAPON_LOCATION);
                SharedPreferenceManager.getInstance().saveBoolean(true, CACHE_CLEARED);

                Toast.makeText(getContext(), "Cache cleared", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
