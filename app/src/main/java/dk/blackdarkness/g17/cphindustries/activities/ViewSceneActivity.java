package dk.blackdarkness.g17.cphindustries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//crashlytics
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import dk.blackdarkness.g17.cphindustries.BuildConfig;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.settings.SettingsActivity;
import dk.blackdarkness.g17.cphindustries.viewfragments.ViewSceneFragment;

public class ViewSceneActivity extends AppCompatActivity {
    public static final String SCENE_ID_KEY = "SCENE_ID";
    public static final String SHOOT_ID_KEY = "SHOOT_ID";
    public static final String WEAPON_ID_KEY = "WEAPON_ID";

    private static final String TAG = "ViewSceneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_view_layout);
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
        //crashlyics
        Fabric.with(this, new Crashlytics());
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
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, getAppVersion(), Toast.LENGTH_SHORT).show();
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

    public String getAppVersion() {
            return "Current application version: " + BuildConfig.VERSION_NAME;
    }
}
