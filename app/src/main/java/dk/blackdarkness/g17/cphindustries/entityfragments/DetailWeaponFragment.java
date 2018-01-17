package dk.blackdarkness.g17.cphindustries.entityfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponDetailsFragment;

import static dk.blackdarkness.g17.cphindustries.dto.FireMode.BURST;
import static dk.blackdarkness.g17.cphindustries.dto.FireMode.FULL_AUTO;
import static dk.blackdarkness.g17.cphindustries.dto.FireMode.SAFE;
import static dk.blackdarkness.g17.cphindustries.dto.FireMode.SINGLE;

public class DetailWeaponFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "DetailWeaponFragment";
    private TextView weaponIpText, weaponMacText, weaponImageDescription,
            weaponNameText, weaponIdText, weaponFiremodeText, weaponShootsText, statusText;
    private FloatingActionButton lock;
    private Button fullAutoButton, safeButton;
    private ImageButton singleButton, burstButton;
    private int sceneId;
    private int shootId;
    private int weaponId;
    private Weapon weapon;
    private WeaponDao weaponDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weapon_details_layout, container, false);
        Log.d(TAG, "onCreateView: Returning.");
        this.lock = view.findViewById(R.id.lockFab);
        this.weaponImageDescription = view.findViewById(R.id.imageDescription);
        this.weaponNameText = view.findViewById(R.id.fr_weapon_details_tvName_description);
        this.weaponIdText = view.findViewById(R.id.fr_weapon_details_tvId_description);
        this.weaponFiremodeText = view.findViewById(R.id.fr_weapon_details_tvFire_mode_description);
        this.weaponShootsText = view.findViewById(R.id.fr_weapon_details_tvShoot_description);
        this.weaponIpText = view.findViewById(R.id.fr_weapon_details_tvIp_description);
        this.weaponMacText = view.findViewById(R.id.fr_weapon_details_tvMac_description);
        this.statusText = view.findViewById(R.id.fr_weapon_details_status_text);

        this.singleButton = view.findViewById(R.id.fr_weapon_details_ibtn_single);
        this.burstButton = view.findViewById(R.id.fr_weapon_details_ibtn_burst);
        this.fullAutoButton = view.findViewById(R.id.fr_weapon_details_btn_full_auto);
        this.safeButton = view.findViewById(R.id.fr_weapon_details_btn_safe);

        this.sceneId = getArguments().getInt(ViewSceneActivity.SCENE_ID_KEY);
        this.shootId = getArguments().getInt(ViewSceneActivity.SHOOT_ID_KEY);
        this.weaponId = getArguments().getInt(ViewSceneActivity.WEAPON_ID_KEY);

        this.weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
        this.weapon = this.weaponDao.getWeapon(weaponId);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity) getActivity()).setActionBarTitle(weapon.getName());
        this.lock.setOnClickListener(this);

        this.weaponNameText.setText(this.weapon.getName());

        this.weaponIdText.setText(Integer.toString(this.weapon.getId()));
        this.weaponFiremodeText.setText(this.weapon.getFireMode().name());
        this.weaponIpText.setText(this.weapon.getIp());
        this.weaponMacText.setText(this.weapon.getMac());

        // TODO: maybe put a limit on the amount of shoots shown or something
        // Set shoots
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (Shoot s : this.weaponDao.getShootsByWeapon(this.weapon.getId())) {
            sb.append(s.getName());
            counter++;
            if(counter != this.weaponDao.getShootsByWeapon(this.weapon.getId()).size())
            sb.append(", ")/*.append("\n")*/;
        }
        this.weaponShootsText.setText(sb);

        // Set warnings
        if (this.weapon.getWarnings().size() > 0) {
            StringBuilder sb2 = new StringBuilder();
            for (int c = 0; c < this.weapon.getWarnings().size(); c++) {
                sb2.append(c+1).append(": ").append(this.weapon.getWarnings().get(c)).append("\n");
            }
            this.statusText.setText(sb2);
        } else {
            this.statusText.setText(R.string.status_text_no_warnings);
        }

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
                Log.d(TAG, "onClick: Trying to open edit weapon details fragment.");
                goToEditWeaponDetailsFragment();
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

    public void goToEditWeaponDetailsFragment() {
        Log.d(TAG, "goToEditWeaponDetailsFragment: Returning");
        Fragment editWeaponDetailsFragment = new EditWeaponDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, this.sceneId);
        bundle.putInt(ViewSceneActivity.SHOOT_ID_KEY, this.shootId);
        bundle.putInt(ViewSceneActivity.WEAPON_ID_KEY, this.weaponId);
        editWeaponDetailsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editWeaponDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    public void setWeaponFiremode(FireMode firemode) {
        Log.d(TAG, "setWeaponFiremode: selected firemode: " + firemode);
        this.weapon.setFireMode(firemode);
        this.weaponDao.update(this.weapon);
        this.weaponFiremodeText.setText(this.weapon.getFireMode().name());
        Log.d(TAG, "setWeaponFiremode: applied firemode: " + this.weaponDao.getWeapon(this.weapon.getId()).getFireMode());
        updateGuiButtonsFiremode();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(this);
        ft.attach(this);
        ft.commit();
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