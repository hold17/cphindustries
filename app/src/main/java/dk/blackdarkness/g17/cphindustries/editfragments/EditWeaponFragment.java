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
import dk.blackdarkness.g17.cphindustries.createfragments.CreateWeaponFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditWeaponFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EditWeaponFragment";

    private TextView dummy;
    private FloatingActionButton lock, add;
    private Fragment createWeaponFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_weapon_layout, container, false);
        dummy = view.findViewById(R.id.editWeaponDummy);
        lock = view.findViewById(R.id.lockFab);
        add = view.findViewById(R.id.createFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Weapons");
        dummy.setText("Edit weapon fragment");
        lock.setOnClickListener(this);
        add.setOnClickListener(this);
        add.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                checkLock();
                break;
            case R.id.createFab:
                goToCreateWeaponFragment();
                break;
        }
    }

    public void checkLock() {
            //Skal erstattes med save data
            Log.d(TAG, "checkLock: Lock pressed. Should save input data.");
            getActivity().onBackPressed();
    }

    public void goToCreateWeaponFragment() {
        Log.d(TAG, "goToCreateWeaponFragment: Returning.");
        createWeaponFragment = new CreateWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createWeaponFragment)
                .addToBackStack(null)
                .commit();
    }
}
