package dk.blackdarkness.g17.cphindustries.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

//crashlytics
import com.crashlytics.android.Crashlytics;

import dk.blackdarkness.g17.cphindustries.helper.SoftInputHelper;
import io.fabric.sdk.android.Fabric;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.menuitems.AboutActivity;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsActivity;
import dk.blackdarkness.g17.cphindustries.viewfragments.ViewSceneFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String SCENE_ID_KEY = "SCENE_ID";
    public static final String SHOOT_ID_KEY = "SHOOT_ID";
    public static final String WEAPON_ID_KEY = "WEAPON_ID";
    private BroadcastReceiver broadcastReceiver;
    private boolean isSoftInputActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view_layout);
        Log.d(TAG, "onCreate: Creating ShotViewActivity");

        //Create custom toolbar, set title and set toolbar as activity actionbar
        Toolbar customToolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(customToolbar);
        setActionBarTitle("Scenes");
        enableActionBar(false);

        //TODO: Check if this causes issues when returning to app after android has killed off it's process.
        // only ADD the fragment if starting after app has been completely killed off
        if (savedInstanceState == null) {
            goToSceneViewFragment();
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


        findViewById(R.id.content).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                findViewById(R.id.content).getWindowVisibleDisplayFrame(r);
                int screenHeight = findViewById(R.id.content).getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                Log.d(TAG, "keypadHeight = " + keypadHeight);

                // 0.15 ratio is perhaps enough to determine keypad height.
                if (keypadHeight > screenHeight * 0.15) {
                    Log.d(TAG,"Software Keyboard was shown");
                    isSoftInputActive = true;
                }
                else {
                    Log.d(TAG, "Software Keyboard was not shown");
                    isSoftInputActive = false;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
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

        if (isSoftInputActive) {
            Log.d(TAG, "onBackPressed: hiding softInput!");
            SoftInputHelper.tryNewThing(getApplicationContext());
        }

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

    public void goToSceneViewFragment() {
        Fragment sceneViewFragment = new ViewSceneFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, sceneViewFragment)
                .commit();
    }

}
