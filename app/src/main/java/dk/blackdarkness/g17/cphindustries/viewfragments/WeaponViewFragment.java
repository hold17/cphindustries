package dk.blackdarkness.g17.cphindustries.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponFragment;
import dk.blackdarkness.g17.cphindustries.entityfragments.WeaponFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class WeaponViewFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "WeaponViewFragment";
    private Button goNext;
    private FloatingActionButton lock;
    private Fragment weaponFragment, editWeaponFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weapon_view_layout, container, false);
        goNext = view.findViewById(R.id.openWeapon);
        lock = view.findViewById(R.id.lockFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Shots");
        goNext.setOnClickListener(this);
        goNext.setText("Weapon #1");
        lock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.openWeapon:
                goToWeaponFragment();
                break;
            case R.id.lockFab:
                Log.d(TAG, "onClick: lockFab. Returning EditWeaponFragment.");
                goToEditWeaponFragment();
                break;
        }
    }

    public void goToWeaponFragment() {
        Log.d(TAG, "onClick: Trying to open weapon fragment.");
        weaponFragment = new WeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weaponFragment).addToBackStack(null).commit();
    }

    public void goToEditWeaponFragment() {
        editWeaponFragment = new EditWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editWeaponFragment)
                .addToBackStack(null)
                .commit();
    }

}
