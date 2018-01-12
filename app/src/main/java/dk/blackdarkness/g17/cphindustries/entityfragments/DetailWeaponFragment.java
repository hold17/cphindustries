package dk.blackdarkness.g17.cphindustries.entityfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

import static dk.blackdarkness.g17.cphindustries.dto.FireMode.BURST;
import static dk.blackdarkness.g17.cphindustries.dto.FireMode.FULL_AUTO;
import static dk.blackdarkness.g17.cphindustries.dto.FireMode.SAFE;
import static dk.blackdarkness.g17.cphindustries.dto.FireMode.SINGLE;

public class DetailWeaponFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DetailWeaponFragment";
    private TextView weaponNameTitle, weaponNameText, weaponIdText, weaponFiremodeText, weaponShootsText, statusText;
    private FloatingActionButton lock;
    private Button fullAutoButton, safeButton;
    ImageButton singleButton, burstButton;
//    private int sceneId;
//    private int shootId;
    private Weapon weapon;
    private WeaponDao weaponDao;

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

//        this.sceneId = getArguments().getInt(ViewSceneActivity.SCENE_ID_KEY);
//        this.shootId = getArguments().getInt(ViewSceneActivity.SHOOT_ID_KEY);
        final int weaponId = getArguments().getInt(ViewSceneActivity.WEAPON_ID_KEY);
        this.weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
        this.weapon = this.weaponDao.get(weaponId);

        this.singleButton = view.findViewById(R.id.fr_weapon_details_ibtn_single);
        this.burstButton = view.findViewById(R.id.fr_weapon_details_ibtn_burst);
        this.fullAutoButton = view.findViewById(R.id.fr_weapon_details_btn_full_auto);
        this.safeButton = view.findViewById(R.id.fr_weapon_details_btn_safe);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity) getActivity()).setActionBarTitle(weapon.getName() + " " + "Details");
//        this.statusText.setText("1: Device could not be connected. Make sure it is turned on and connected to the network.");
        this.weaponNameTitle.setText(this.weapon.getName());
        this.weaponNameText.setText(this.weapon.getName());

        this.weaponIdText.setText(Integer.toString(this.weapon.getId()));
        //this.weaponIdText.setText("" + this.weapon.getId());
        this.weaponFiremodeText.setText(this.weapon.getFireMode().name());
        this.weaponShootsText.setText("Shoots will go here...");
        // Set warnings
        if (this.weapon.getWarnings().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < this.weapon.getWarnings().size(); c++) {
                sb.append(c).append(1).append(" ").append(this.weapon.getWarnings().get(c)).append("\n");
            }
            this.statusText.setText(sb);
        } else {
            this.statusText.setText("No warnings.");
        }
        this.lock.setOnClickListener(this);

        updateGuiButtonsFiremode();

        this.singleButton.setOnClickListener(this);
        this.burstButton.setOnClickListener(this);
        this.fullAutoButton.setOnClickListener(this);
        this.safeButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                //Edit view should be different from the one navigated to
                //from ViewWeaponFragment. Edit this one weapon only?
                Log.d(TAG, "onClick: Trying to open edit weapon fragment.");
                goToEditWeaponFragment();
                break;
            case R.id.fr_weapon_details_ibtn_single:
                setWeaponFiremode(SINGLE);
                break;
            case R.id.fr_weapon_details_ibtn_burst:
                setWeaponFiremode(BURST);
                break;
            case R.id.fr_weapon_details_btn_full_auto:
                setWeaponFiremode(FULL_AUTO);
                break;
            case R.id.fr_weapon_details_btn_safe:
                setWeaponFiremode(SAFE);
                break;

        }
    }

    public void goToEditWeaponFragment() {
        Log.d(TAG, "goToEditWeaponFragment: Returning");

        Toast.makeText(getContext(), "Not implemented yet", Toast.LENGTH_LONG).show();
    }

    public void setWeaponFiremode(FireMode theEnum) {
        Log.d(TAG, "setWeaponFiremode: selected firemode: " + theEnum);
        this.weapon.setFireMode(theEnum);
        this.weaponDao.update(this.weapon.getId(),this.weapon);
        Log.d(TAG, "setWeaponFiremode: applied firemode: " + this.weaponDao.get(this.weapon.getId()).getFireMode());
        updateGuiButtonsFiremode();
    }

    public void updateGuiButtonsFiremode() {

        //Resets buttons
        singleButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_left));
        burstButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_center));
        fullAutoButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_center));
        safeButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_right));

        //Sets pressed button
        switch (this.weapon.getFireMode()) {
            case SINGLE:
                singleButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_pressed_left));
                break;
            case BURST:
                burstButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_pressed_center));
                break;
            case FULL_AUTO:
                fullAutoButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_pressed_center));
                break;
            case SAFE:
                safeButton.setBackground(getResources().getDrawable(R.drawable.buttonshape_pressed_rigth));
                break;
        }
    }
}