package dk.blackdarkness.g17.cphindustries.entityfragments;

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
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class WeaponFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "WeaponFragment";
    private Fragment editWeaponFragment;
    private TextView dummy;
    private FloatingActionButton lock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weapon_layout, container, false);
        dummy = view.findViewById(R.id.weaponDummy);
        lock = view.findViewById(R.id.lockFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Weapons");
        dummy.setText("Info about weapon here");
        lock.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                //Edit view should be different from the one navigated to
                //from WeaponViewFragment. Edit this one weapon only?
                Log.d(TAG, "onClick: Trying to open edit weapon fragment.");
                goToEditWeaponFragment();
        }
    }

    public void goToEditWeaponFragment() {
        editWeaponFragment = new EditWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editWeaponFragment)
                .addToBackStack(null)
                .commit();
    }
}