package dk.blackdarkness.g17.cphindustries.createfragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootWeaponDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

public class CreateWeaponFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "CreateWeaponFragment";
    private View view;
    private TextView submitSave, submitCancel;
    private RadioGroup rgLeft, rgRight;
    private ConstraintLayout radioButtons, loading;
    private EditText weaponNameText;
    //private int sceneId;
    private int shootId;
    private WeaponDao weaponDao;
    private ShootWeaponDao shootWeaponDao;
    private final int buttonTextDisabled = 0x4CDE6305;
    private final int buttonTextEnabled = 0xFFDE6305;
    private int selectedWeapon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_weapon_layout, container, false);

        submitSave = view.findViewById(R.id.fr_create_weapon_tvSave);
        submitCancel = view.findViewById(R.id.fr_create_weapon_tvCancel);

        rgLeft = view.findViewById(R.id.fr_create_weapon_radioGroup_left);
        rgRight = view.findViewById(R.id.fr_create_weapon_radioGroup_right);

        radioButtons = view.findViewById(R.id.fr_create_weapon_constraintLayout_bottom);
        loading = view.findViewById(R.id.fr_create_weapon_constraintLayout_loading);

        weaponNameText = view.findViewById(R.id.fr_create_weapon_editText);

        weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();

        shootWeaponDao = ApplicationConfig.getDaoFactory().getShootWeaponDao();

        //sceneId = getArguments().getInt(ViewSceneActivity.SCENE_ID_KEY);
        shootId = getArguments().getInt(ViewSceneActivity.SHOOT_ID_KEY);

        initLayout();
        Log.d(TAG, "onCreateView: Returning");
        return view;
    }

    public void initLayout() {
        getActivity().setTitle("Create Weapon");

        radioButtons.setVisibility(View.GONE);

        submitSave.setOnClickListener(this);
        submitSave.setText("Submit");
        submitSave.setEnabled(false);
        submitSave.setTextColor(buttonTextDisabled);

        submitCancel.setOnClickListener(this);
        submitCancel.setText("Cancel");

        rgLeft.setOnCheckedChangeListener(lis1);
        rgRight.setOnCheckedChangeListener(lis2);

        new getKnownDevice().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_create_weapon_tvSave:
                //TODO: Figure out a good way to check if a name has been written and enable/disable submit accordingly
                Log.d(TAG, "onClick: submit clicked - weaponNameText: " + weaponNameText.getText().toString());
                if (weaponNameText.getText().toString().equals(""))
                    break;
                saveClicked();
                getActivity().onBackPressed();
                break;
            case R.id.fr_create_weapon_tvCancel:
                getActivity().onBackPressed();
                break;
        }
    }

    private void setSelectedWeapon() {
        if (rgLeft.getCheckedRadioButtonId() != -1) {
            this.selectedWeapon =  rgLeft.getCheckedRadioButtonId();
            Log.d(TAG, "saveClicked: weapon selected: " + this.weaponDao.getWeapon(selectedWeapon).getMac());
            submitSave.setEnabled(true);
            submitSave.setTextColor(buttonTextEnabled);
        }
        else if (rgRight.getCheckedRadioButtonId() != -1) {
            this.selectedWeapon =  rgRight.getCheckedRadioButtonId();
            Log.d(TAG, "saveClicked: weapon selected: " + this.weaponDao.getWeapon(selectedWeapon).getMac());
            submitSave.setEnabled(true);
            submitSave.setTextColor(buttonTextEnabled);
        }}

    private void saveClicked() {
        Weapon selectedWep = this.weaponDao.getWeapon(this.selectedWeapon);
        final Weapon newWeapon = new Weapon(-1, this.weaponNameText.getText().toString(), selectedWep.getIp(), selectedWep.getMac());
        System.out.println("Creating shoot: " + newWeapon);
        this.weaponDao.create(newWeapon);
        int newWepId = newWeapon.getId();
        final ShootWeapon newShootWeapon = new ShootWeapon(-1, this.shootId, newWepId);
        this.shootWeaponDao.create(newShootWeapon);
    }

    // swaps listeners when unchecking radiobutton as you'd otherwise be able to check items in both groups
    private RadioGroup.OnCheckedChangeListener lis1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.d(TAG, "rgLeft 1: child checked: " + rgLeft.getCheckedRadioButtonId());
            Log.d(TAG, "rgRight 1: child checked: " + rgRight.getCheckedRadioButtonId());
            if (checkedId != -1) {
                rgRight.setOnCheckedChangeListener(null);
                rgRight.clearCheck();
                rgRight.setOnCheckedChangeListener(lis2);
                setSelectedWeapon();
            }
        }
    };

    // see comment above
    private RadioGroup.OnCheckedChangeListener lis2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.d(TAG, "rgLeft 2: child checked: " + rgLeft.getCheckedRadioButtonId());
            Log.d(TAG, "rgRight 2: child checked: " + rgRight.getCheckedRadioButtonId());
            if (checkedId != -1) {
                rgLeft.setOnCheckedChangeListener(null);
                rgLeft.clearCheck();
                rgLeft.setOnCheckedChangeListener(lis1);
                setSelectedWeapon();
            }
        }
    };

    private class getKnownDevice extends AsyncTask<String, Void, String> {
        private ArrayList<Weapon> weapons;

        @Override
        protected String doInBackground(String... params) {
            try {
                // Fetch ip / mac addresses from known devices
                this.weapons = new ArrayList<>();
                this.weapons.addAll(weaponDao.getList());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // Setup radio button view with found weapons
            int rgHalf = (weapons.size()) / 2;
            final RadioButton[] rb = new RadioButton[weapons.size()];
            for (int i = 0; i < weapons.size(); i++) {
                if (i <= rgHalf - 1) {
                    rb[i] = new RadioButton(getContext());
                    rb[i].setText(weapons.get(i).getMac());
                    rb[i].setId(weapons.get(i).getId());
                    rgRight.addView(rb[i]);
                } else {
                    rb[i] = new RadioButton(getContext());
                    rb[i].setText(weapons.get(i).getMac());
                    rb[i].setId(weapons.get(i).getId());
                    rgLeft.addView(rb[i]);
                }
                radioButtons.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                Log.d(TAG, "onPostExecute - Added weapon with MAC: "  + weapons.get(i).getMac());
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}

