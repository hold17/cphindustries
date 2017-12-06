package dk.blackdarkness.g17.cphindustries.activities;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.MenuItem;

        import dk.blackdarkness.g17.cphindustries.R;
        import dk.blackdarkness.g17.cphindustries.viewfragments.ShotViewFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class ShotViewActivity extends AppCompatActivity {

    private static final String TAG = "ShotViewActivity";
    private Fragment shotViewFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Creating ShotViewActivity");
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        initShotViewFragment();

        setContentView(R.layout.activity_shot_view_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            //Når der bliver trykket home, tjek om vi skal tilbage til start-activity
            //Hvis vi skal, så start den på ny
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (f instanceof ShotViewFragment) {
                Intent backToSceneView = new Intent(this, SceneViewActivity.class);
                startActivity(backToSceneView);
                return true;
            }
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initShotViewFragment() {
        System.out.println("Creating shoot");
        int sceneId = getIntent().getIntExtra("SCENE_ID", -1);
        System.out.println("Scene ID = " + sceneId);
//        savedInstanceState.putInt("SCENE_ID", getIntent().getIntExtra("SCENE_ID", -1));
        System.out.println("Saving...");
        Bundle bundle = new Bundle();
        bundle.putInt("SCENE_ID", sceneId);

        shotViewFragment = new ShotViewFragment();

        shotViewFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, shotViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
