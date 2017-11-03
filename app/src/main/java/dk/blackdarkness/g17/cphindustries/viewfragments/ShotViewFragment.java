package dk.blackdarkness.g17.cphindustries.viewfragments;

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
import dk.blackdarkness.g17.cphindustries.createfragments.CreateShotFragment;
import dk.blackdarkness.g17.cphindustries.editfragments.EditShotFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class ShotViewFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ShotViewFragment";
    private Fragment weaponViewFragment, createShotFragment, editShotFragment;
    private FloatingActionButton add, lock;
    private Button open, edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shot_view_layout, container, false);
        open = view.findViewById(R.id.openShot);
        lock = view.findViewById(R.id.lockShotView);
        add = view.findViewById(R.id.createShot);
        edit = view.findViewById(R.id.editShot);
        initLayout();
        return view;
    }

    public void initLayout() {
        lock.setOnClickListener(this);
        add.setOnClickListener(this);
        getActivity().setTitle("Shots");
        open.setOnClickListener(this);
        open.setText("Shot #1");
        edit.setOnClickListener(this);
        edit.setText("Edit shot");
        checkInitLock();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.openShot:
                Log.d(TAG, "onClick: Trying to open weaponview fragment.");
                weaponViewFragment = new WeaponViewFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, weaponViewFragment).addToBackStack(null).commit();
                break;
            case R.id.createShot:
                Log.d(TAG, "onClick: CreateShot has been clicked.");
                createShotFragment = new CreateShotFragment();
                createShotFragment = new CreateShotFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, createShotFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.editShot:
                editShotFragment = new EditShotFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, editShotFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.lockShotView:
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
            SceneViewActivity.locked = false;
        }
    }

}
