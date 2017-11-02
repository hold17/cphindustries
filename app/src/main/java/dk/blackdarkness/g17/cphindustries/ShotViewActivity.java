package dk.blackdarkness.g17.cphindustries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * Created by Thoma on 11/02/2017.
 */

public class ShotViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ShotViewActivity";
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private Fragment shotViewFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        shotViewFragment = new ShotViewFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, shotViewFragment);
        fragmentTransaction.commit();
        setContentView(R.layout.activity_shot_view_layout);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
