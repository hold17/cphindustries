package dk.blackdarkness.g17.cphindustries.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;

import static android.content.ContentValues.TAG;

public class SettingsFragment extends PreferenceFragmentCompat implements android.support.v7.preference.Preference.OnPreferenceClickListener {
    public static final String KEY_PREF_CLEAR_SETTINGS = "clearSettings";
    public static final String KEY_PREF_CLEAR_CACHE = "clearCache";
    public static final String KEY_PREF_DEMO_DATA = "demoDataSwitch";
    public static final String KEY_PREF_ORIENTATION = "orientationSwitch";
    public static final String KEY_PREF_UPDATE_RATE= "updateRate";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        android.support.v7.preference.Preference clearSettings = findPreference(KEY_PREF_CLEAR_SETTINGS);
        clearSettings.setOnPreferenceClickListener(this);

        android.support.v7.preference.Preference clearCache = findPreference(KEY_PREF_CLEAR_CACHE);
        clearCache.setOnPreferenceClickListener(this);

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
                Toast.makeText(getContext(), "Clear settings", Toast.LENGTH_SHORT).show();
                return true;
            case KEY_PREF_CLEAR_CACHE:
                Toast.makeText(getContext(), "Clear cache", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
