package dk.blackdarkness.g17.cphindustries.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.viewfragments.SceneViewFragment;

public class SceneViewActivity extends AppCompatActivity {

    public static boolean locked = true;

    private static final String TAG = "SceneViewActivity";
    private Fragment sceneViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_view_layout);
        Log.d(TAG, "onCreate: Creating ShotViewActivity");
        sceneViewFragment = new SceneViewFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.scene_fragment_container, sceneViewFragment)
                .addToBackStack(null)
                .commit();
        setContentView(R.layout.activity_scene_view_layout);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Used to handle action bar activities
        //Specify ID and perform action
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void resetActionBar(Boolean homeIndicator) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeIndicator);
        getSupportActionBar().setHomeButtonEnabled(homeIndicator);
    }


}
