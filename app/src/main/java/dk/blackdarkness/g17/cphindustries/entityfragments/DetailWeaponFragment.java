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

public class DetailWeaponFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DetailWeaponFragment";
    private TextView statusText;
    private FloatingActionButton lock;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weapon_details_layout, container, false);
        this.statusText = view.findViewById(R.id.fr_weapon_details_status_text);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weapon Details");
        this.statusText.setText("1: Device could not be connected. Make sure it is turned on and connected to the network.");
        this.lock.setOnClickListener(this);
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
        Log.d(TAG, "goToEditWeaponFragment: Returning");
        Fragment editWeaponFragment = new EditWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editWeaponFragment)
                .addToBackStack(null)
                .commit();
    }
}