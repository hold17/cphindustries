package dk.blackdarkness.g17.cphindustries.viewfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.activities.ShotViewActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateSceneFragment;
import dk.blackdarkness.g17.cphindustries.editfragments.EditSceneFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class SceneViewFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SceneViewFragment";
    private Fragment editSceneFragment, createSceneFragment;
    private FloatingActionButton add, lock;
    private Button goToShotsView, editScene;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scene_view_layout, container, false);
        goToShotsView = view.findViewById(R.id.openScene);
        editScene = view.findViewById(R.id.editScene);
        add = view.findViewById(R.id.createScene);
        lock = view.findViewById(R.id.lockSceneView);
        initLayout();
        Log.d(TAG, "onCreateView: Locked status: " + SceneViewActivity.locked);
        return view;
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }


    public void initLayout() {
        getActivity().setTitle("Scenes");
        add.setOnClickListener(this);
        lock.setOnClickListener(this);

        //erstattes med liste af nuværende scener
        goToShotsView.setOnClickListener(this);
        goToShotsView.setText("Scene #1");

        //Erstattes med edit symbol
        editScene.setOnClickListener(this);
        editScene.setText("Edit scene");

        //Check om låsen er slået til ved opstart og fjern homesymbol (dette er startsiden)
        checkInitLock();
        ((SceneViewActivity)getActivity()).resetActionBar(false);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.openScene:
                Intent shotView = new Intent(getActivity(), ShotViewActivity.class);
                Log.d(TAG, "onClick: Testbutton. Attempting to start shotview activity.");
                startActivity(shotView);
                getActivity().finish();
                break;
            case R.id.editScene:
                editSceneFragment = new EditSceneFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.scene_fragment_container, editSceneFragment).
                        addToBackStack(null).commit();
                break;
            case R.id.createScene:
                Log.d(TAG, "onClick: Pressed add in scene view.");
                createSceneFragment = new CreateSceneFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.scene_fragment_container, createSceneFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.lockSceneView:
                checkLock();
                break;
        }
    }

    public void checkLock() {
        if (SceneViewActivity.locked) {
            //Setbackgroundcolor virker ikke - skal også have setImageResource(R.drawable), så låsen skifter til åben
            lock.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
            add.setVisibility(View.VISIBLE);
            SceneViewActivity.locked = false;
        } else {
            //Samme her
            lock.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimarySmoke));
            add.setVisibility(View.GONE);
            SceneViewActivity.locked = true;
        }

    }

    //Tjek om låsen er slået til ved fragment opstart
    //Hvis den ikke er slået til, lås op - ellers gør intet (låst pr. default)
    public void checkInitLock() {
        if(!SceneViewActivity.locked) {
            lock.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
            add.setVisibility(View.VISIBLE);
        }
    }
}
