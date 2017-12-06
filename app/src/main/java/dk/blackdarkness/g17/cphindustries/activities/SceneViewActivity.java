package dk.blackdarkness.g17.cphindustries.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.viewfragments.SceneViewFragment;

public class SceneViewActivity extends AppCompatActivity {

    private static final String TAG = "SceneViewActivity";
    private Fragment sceneViewFragment;

    /*
    ##Bliver ikke brugt lige nu - er til actionbar metode, linie 54
    private String back = "back";
    private String cancel = "cancel";
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_view_layout);
        Log.d(TAG, "onCreate: Creating ShotViewActivity");

        //Første fragment føjes til scene_fragment_container med .add()
        //Herefter bliver der brugt .replace() for at erstatte det
        initSceneViewFragment();

        //Aktivitetens contentview bliver sat
        //I layout filen er der et FragmentLayout, der nu indeholder et SceneViewFragment.
        setContentView(R.layout.activity_scene_view_layout);
    }

    //Used to handle action bar activities
    //Specify ID and perform action
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Forsøg evt. med resetActionBar(Boolean display, String type)
    //type.equals(this.cancel) el. (this.back) for at sætte det rigtige
    //ikon i actionbar. Måske skal der custom action bar til!
    public void resetActionBar(Boolean display) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(display);
        getSupportActionBar().setHomeButtonEnabled(display);
    }

    public void initSceneViewFragment() {
        sceneViewFragment = new SceneViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.scene_fragment_container, sceneViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
