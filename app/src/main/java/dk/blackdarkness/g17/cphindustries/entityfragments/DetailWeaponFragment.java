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
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class DetailWeaponFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DetailWeaponFragment";
    private TextView weaponNameTitle, weaponNameText, weaponIdText, weaponFiremodeText, weaponShootsText, statusText;
    private FloatingActionButton lock;
    private int sceneId;
    private int shootId;
    private Weapon weapon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weapon_details_layout, container, false);

        this.weaponNameTitle = view.findViewById(R.id.fr_weapon_details_title);
        this.weaponNameText = view.findViewById(R.id.fr_weapon_details_tvName_description);
        this.weaponIdText = view.findViewById(R.id.fr_weapon_details_tvId_description);
        this.weaponFiremodeText = view.findViewById(R.id.fr_weapon_details_tvFire_mode_description);
        this.weaponShootsText = view.findViewById(R.id.fr_weapon_details_tvShoot_description);

        this.statusText = view.findViewById(R.id.fr_weapon_details_status_text);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneId = getArguments().getInt("SCENE_ID");
        this.shootId = getArguments().getInt("SHOOT_ID");
        final int weaponId = getArguments().getInt("WEAPON_ID");
        this.weapon = ApplicationConfig.getDaoFactory().getWeaponDao().get(sceneId, shootId, weaponId);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SceneViewActivity)getActivity()).setActionBarTitle(weapon.getName() + " " + "Details");
//        this.statusText.setText("1: Device could not be connected. Make sure it is turned on and connected to the network.");
        this.weaponNameTitle.setText(this.weapon.getName());
        this.weaponNameText.setText(this.weapon.getName());

        this.weaponIdText.setText(Integer.toString(this.weapon.getId()));
        this.weaponIdText.setText("" + this.weapon.getId());
        this.weaponFiremodeText.setText(this.weapon.getFireMode().name());
        this.weaponShootsText.setText("Shoots will go here...");
        // Set warnings
        if (this.weapon.getWarnings().size() > 0) {
            String warnings = "";
            for (int c = 0; c < this.weapon.getWarnings().size(); c++) {
                warnings += c + 1 + " " + this.weapon.getWarnings().get(c) + "\n";
            }
            this.statusText.setText(warnings);
        } else {
            this.statusText.setText("No warnings.");
        }
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