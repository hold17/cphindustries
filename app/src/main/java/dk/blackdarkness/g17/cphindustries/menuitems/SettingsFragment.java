package dk.blackdarkness.g17.cphindustries.menuitems;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import dk.blackdarkness.g17.cphindustries.BuildConfig;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat implements android.support.v7.preference.Preference.OnPreferenceClickListener {

    private static final String TAG = "SettingsFragment";

    //Settings keys
    private static final String KEY_PREF_RESET_APP= "resetApp";
    private static final String KEY_PREF_CLEAR_CACHE = "clearCache";
    private static final String KEY_PREF_DEMO_DATA = "demoDataSwitch";
    private static final String KEY_PREF_ORIENTATION = "orientationSwitch";
    private static final String KEY_PREF_UPDATE_RATE= "updateRate";
    private static final String KEY_PREF_BUILD_VERSION = "buildVersion";

    //SharedPreferences locations
    public static final String CACHE_CLEARED = "cacheCleared";
    public static final String APP_RESET = "appReset";
    private static final String SAVED_SCENES_LOCATION = "SAVED_SCENES_LIST";
    private static final String SAVED_SHOOTS_LOCATION = "SAVED_SHOOTS_LIST";
    private static final String SAVED_WEAPONS_LOCATION = "SAVED_WEAPONS_LIST";
    private static final String SAVED_SHOOTWEAPON_LOCATION = "SAVED_SHOOTWEAPON_LIST";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        android.support.v7.preference.Preference clearSettings = findPreference(KEY_PREF_RESET_APP);
        clearSettings.setOnPreferenceClickListener(this);

        android.support.v7.preference.Preference clearCache = findPreference(KEY_PREF_CLEAR_CACHE);
        clearCache.setOnPreferenceClickListener(this);

        android.support.v7.preference.Preference buildVersion = findPreference(KEY_PREF_BUILD_VERSION);
        buildVersion.setSummary("v" + BuildConfig.VERSION_NAME);


        /****************************************************
        BELOW CODE IN onCreate() IS FOR TEST PURPOSES ONLY
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

        switch(preference.getKey()) {
            case KEY_PREF_RESET_APP:
                AlertDialog.Builder a_builderSettings = new AlertDialog.Builder(getContext());
                a_builderSettings.setMessage(R.string.reset_app__alert)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferenceManager.getInstance().clear();
                                SharedPreferenceManager.getInstance().saveBoolean(true, APP_RESET);
                                restartMainActivity();
                                Toast.makeText(getContext(), R.string.application_reset, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), R.string.reset_cancelled, Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertSettings = a_builderSettings.create();
                alertSettings.setTitle(R.string.warning);
                alertSettings.show();
                return true;
            case KEY_PREF_CLEAR_CACHE:
                AlertDialog.Builder a_builderCache = new AlertDialog.Builder(getContext());
                a_builderCache.setMessage(R.string.clear_cache_alert)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferenceManager.getInstance().clear(SAVED_SCENES_LOCATION);
                                SharedPreferenceManager.getInstance().clear(SAVED_SHOOTS_LOCATION);
                                SharedPreferenceManager.getInstance().clear(SAVED_WEAPONS_LOCATION);
                                SharedPreferenceManager.getInstance().clear(SAVED_SHOOTWEAPON_LOCATION);
                                SharedPreferenceManager.getInstance().saveBoolean(true, CACHE_CLEARED);
                                restartMainActivity();
                                Toast.makeText(getContext(), R.string.cache_cleared, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), R.string.cache_clear_cancelled, Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alertCache = a_builderCache.create();
                alertCache.setTitle(R.string.warning);
                alertCache.show();
                return true;
        }
        return false;
    }

    public void restartMainActivity() {
        //finish() ViewSceneActivity by broadcasting action "finish",
        //which is picked up by the intent-filter in the activity's broadcast receiver
        Intent intent = new Intent("finish");
        getContext().sendBroadcast(intent);

        //Start activity anew
        startActivity(new Intent(getContext(), ViewSceneActivity.class));
    }
}
