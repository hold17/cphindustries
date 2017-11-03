package dk.blackdarkness.g17.cphindustries;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class SceneViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SceneViewActivity";

    private FloatingActionButton fab;
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_view_layout);

        test = (Button) findViewById(R.id.test);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        initLayout();
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    public void initLayout() {
        fab.setOnClickListener(this);
        test.setOnClickListener(this);
        test.setText("Go to shot activity");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up buttonshape_right, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fab:
                Log.d(TAG, "onClick: Fab buttonshape_right.");
                break;
            case R.id.test:
                Intent shotView = new Intent(this, ShotViewActivity.class);
                Log.d(TAG, "onClick: Testbutton. Attempting to start shotview activity.");
                startActivity(shotView);
        }
    }
}
