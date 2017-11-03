package dk.blackdarkness.g17.cphindustries.editfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateSceneFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditSceneFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EditSceneFragment";

    private TextView dummy;
    private FloatingActionButton add, lock;
    private Fragment createSceneFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_scene_layout, container, false);
        dummy = view.findViewById(R.id.editSceneDummy);
        add = view.findViewById(R.id.createFab);
        lock = view.findViewById(R.id.lockFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Scenes");
        ((SceneViewActivity)getActivity()).resetActionBar(true);
        dummy.setText("<Scenes in edittext list view here>");
        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(this);
        lock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                checkLock();
                break;
            case R.id.createFab:
                goToCreateSceneFragment();
                break;
        }
    }

    public void checkLock() {
        Log.d(TAG, "checkLock: Should save input data.");
        getActivity().onBackPressed();
    }

    public void goToCreateSceneFragment() {
        createSceneFragment = new CreateSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.scene_fragment_container, createSceneFragment)
                .addToBackStack(null)
                .commit();
    }

}
