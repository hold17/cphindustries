package dk.blackdarkness.g17.cphindustries.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.viewfragments.SceneViewFragment;

public class SceneViewActivity extends AppCompatActivity {
    public static final String SCENE_ID_KEY = "SCENE_ID";
    public static final String SHOOT_ID_KEY = "SHOOT_ID";
    public static final String WEAPON_ID_KEY = "WEAPON_ID";

    private static final String TAG = "SceneViewActivity";

//    TODO: Bliver ikke brugt lige nu - er til actionbar metode, linie 53
//    private String back = "back";
//    private String cancel = "cancel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_view_layout);
        Log.d(TAG, "onCreate: Creating ShotViewActivity");
        enableActionBar(false);

        // only ADD the fragment if starting after app has been completely killed off
        if (savedInstanceState == null) {
            initSceneViewFragment();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // if we're not yet back at sceneViewFragment, pop stack
        if (fragmentManager.getBackStackEntryCount() > 1) {
            //fragmentManager.popBackStack();
            //TODO: find better method than immediate
            fragmentManager.popBackStackImmediate();
            // if after popping stack we're back at sceneViewFragment, disable 'up' button
            Fragment f = fragmentManager.findFragmentById(R.id.fragment_container);
            if (f instanceof SceneViewFragment) {
                enableActionBar(false);
            }
        } else {
            // if we're on sceneViewFragment, finish activity and go to dashboard
            finish();
        }
    }

    //Forsøg evt. med enableActionBar(Boolean display, String type)
    //type.equals(this.cancel) el. (this.back) for at sætte det rigtige
    //ikon i actionbar. Måske skal der custom action bar til!
    public void enableActionBar(Boolean display) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(display);
        getSupportActionBar().setHomeButtonEnabled(display);
    }

    public void initSceneViewFragment() {
        Fragment sceneViewFragment = new SceneViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, sceneViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
