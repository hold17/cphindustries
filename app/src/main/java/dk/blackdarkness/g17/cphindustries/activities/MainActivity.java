package dk.blackdarkness.g17.cphindustries.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//crashlytics
import com.crashlytics.android.Crashlytics;

import dk.blackdarkness.g17.cphindustries.helper.FragmentType;
import dk.blackdarkness.g17.cphindustries.helper.SoftInputHelper;
import dk.blackdarkness.g17.cphindustries.viewfragments.ViewFragment;
import io.fabric.sdk.android.Fabric;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.menuitems.AboutActivity;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String FRAGMENT_TYPE_KEY = "FRAGMENT_TYPE";
    public static final String SCENE_ID_KEY = "SCENE_ID";
    public static final String SHOOT_ID_KEY = "SHOOT_ID";
    public static final String WEAPON_ID_KEY = "WEAPON_ID";
    public static final String SUBTITLE_KEY = "SUBTITLE";
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Creating ShotViewActivity");

        //Create custom toolbar, set title and set toolbar as activity actionbar
        Toolbar customToolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(customToolbar);
        enableActionBar(false);

        //TODO: Check if this causes issues when returning to app after android has killed off it's process.
        // only ADD the fragment if starting after app has been completely killed off
        if (savedInstanceState == null) {
            goToViewFragment();
        }
        //crashlytics
        Fabric.with(this, new Crashlytics());

        //Broadcast receiver to finish activity from within Settings
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish")) {
                    unregisterReceiver(broadcastReceiver);
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: up button pressed!");
                // hide softInput if open
                SoftInputHelper.hideSoftInput(this, this.getCurrentFocus());
                onBackPressed();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        // if we're back at sceneViewFragment, disable 'up' button
        
        if (fragmentManager.getBackStackEntryCount() == 0) {
            enableActionBar(false);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: Configuration change.");

        boolean allowLandScapeMode = SharedPreferenceManager.getInstance().getBoolean("orientationSwitch");
        Log.d(TAG, "onConfigurationChanged: allowLandScapeMode: " + allowLandScapeMode);
        if (allowLandScapeMode) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
            Log.d(TAG, "onConfigurationChanged: Orientation allowed.");
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void enableActionBar(Boolean display) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(display);
        getSupportActionBar().setHomeButtonEnabled(display);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void setActionBarSubtitle(String title) {
        getSupportActionBar().setSubtitle(title);
    }

    public void goToViewFragment() {
        Fragment viewFragment = new ViewFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(FRAGMENT_TYPE_KEY, FragmentType.SCENES);
        viewFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, viewFragment)
                .commit();
    }

}
