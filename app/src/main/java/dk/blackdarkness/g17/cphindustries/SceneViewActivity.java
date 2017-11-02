package dk.blackdarkness.g17.cphindustries;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class SceneViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SceneViewActivity";
    public static boolean locked = true;
    private FloatingActionButton add, lock;
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_view_layout);
        test = (Button) findViewById(R.id.test);
        add = (FloatingActionButton) findViewById(R.id.createScene);
        lock = (FloatingActionButton) findViewById(R.id.lockSceneView);
        initLayout();
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    public void initLayout() {
        add.setOnClickListener(this);
        lock.setOnClickListener(this);
        test.setOnClickListener(this);
        test.setText("Go to shot activity");
        checkInitLock();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.test:
                Intent shotView = new Intent(this, ShotViewActivity.class);
                Log.d(TAG, "onClick: Testbutton. Attempting to start shotview activity.");
                startActivity(shotView);
            case R.id.createScene:
                Log.d(TAG, "onClick: Pressed add in scene view.");
                break;
            case R.id.lockSceneView:
                checkLock();
        }
    }

    public void checkLock() {
        if (locked) {
            //Setbackgroundcolor virker ikke - skal også have setImageResource(R.drawable), så låsen skifter til åben
            lock.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            add.setVisibility(View.VISIBLE);
            locked = false;
        } else {
            //Samme her
            lock.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimarySmoke));
            add.setVisibility(View.GONE);
            locked = true;
        }

    }

    //Tjek om låsen er slået til ved fragment opstart
    //Hvis den ikke er slået til, lås op - ellers gør intet (låst pr. default)
    public void checkInitLock() {
        if(!locked) {
            lock.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            add.setVisibility(View.VISIBLE);
            locked = false;
        }
    }

}
