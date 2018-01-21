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
import dk.blackdarkness.g17.cphindustries.activities.MainActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootWeaponDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.helper.SoftInputHelper;

import static android.widget.RadioGroup.OnCheckedChangeListener;

public class CreateWeaponFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "CreateWeaponFragment";
    private TextView submitSave, submitCancel;
    private RadioGroup rgLeft, rgRight;
    private ConstraintLayout radioButtons, loading;
    private EditText weaponNameText;
    private int sceneId;
    private int shootId;
    private int selectedWeapon;
    private WeaponDao weaponDao;
    private ShootWeaponDao shootWeaponDao;
    private final int buttonTextDisabled = 0x4CDE6305;
    private final int buttonTextEnabled = 0xFFDE6305;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_weapon, container, false);
        Log.d(TAG, "onCreateView: Returning");

        weaponNameText = view.findViewById(R.id.fr_create_weapon_editText);

        radioButtons = view.findViewById(R.id.fr_create_weapon_constraintLayout_bottom);
        loading = view.findViewById(R.id.fr_create_weapon_constraintLayout_loading);

        rgLeft = view.findViewById(R.id.fr_create_weapon_radioGroup_left);
        rgRight = view.findViewById(R.id.fr_create_weapon_radioGroup_right);

        submitSave = view.findViewById(R.id.fr_create_weapon_tvSave);
        submitCancel = view.findViewById(R.id.fr_create_weapon_tvCancel);

        this.sceneId = getArguments().getInt(MainActivity.SCENE_ID_KEY);
        this.shootId = getArguments().getInt(MainActivity.SHOOT_ID_KEY);

        this.weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
        this.shootWeaponDao = ApplicationConfig.getDaoFactory().getShootWeaponDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Create Weapon");

        view.setOnClickListener(this);

        radioButtons.setVisibility(View.GONE);

        submitSave.setOnClickListener(this);
        submitSave.setText(R.string.submit);
        submitSave.setEnabled(false);
        submitSave.setTextColor(buttonTextDisabled);

        submitCancel.setOnClickListener(this);
        submitCancel.setText(R.string.cancel);

        rgLeft.setOnCheckedChangeListener(lis1);
        rgRight.setOnCheckedChangeListener(lis2);

        new getKnownDevice().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_create_weapon_tvSave:
                Log.d(TAG, "onClick: submit clicked - weaponNameText: " + weaponNameText.getText().toString());
                //TODO: Figure out a good way to check if a name has been written and enable/disable submit accordingly
                if (weaponNameText.getText().toString().equals(""))
                    break;
                saveClicked();
                SoftInputHelper.hideSoftInput(getContext(), view);
                getActivity().onBackPressed();
                break;
            case R.id.fr_create_weapon_tvCancel:
                SoftInputHelper.hideSoftInput(getContext(), view);
                getActivity().onBackPressed();
                break;
            case R.id.fr_create_weapon_layout:
                Log.d(TAG, "onClick: requesting focus!");
                SoftInputHelper.hideSoftInput(getContext(), view);
                break;
        }
    }

    private void setSelectedWeapon() {
        if (rgLeft.getCheckedRadioButtonId() != -1) {
            this.selectedWeapon =  rgLeft.getCheckedRadioButtonId();
        }
        else if (rgRight.getCheckedRadioButtonId() != -1) {
            this.selectedWeapon =  rgRight.getCheckedRadioButtonId();
        }
        else {
            // no radiobutton checked, keep submit disabled
            return;
        }
        Log.d(TAG, "saveClicked: weapon selected: " + this.weaponDao.getWeapon(selectedWeapon).getMac());
        submitSave.setEnabled(true);
        submitSave.setTextColor(buttonTextEnabled);
    }

    private void saveClicked() {
        Weapon selectedWep = this.weaponDao.getWeapon(this.selectedWeapon);

        final Weapon newWeapon = new Weapon(-1, this.weaponNameText.getText().toString(), selectedWep.getIp(), selectedWep.getMac());
        Log.d(TAG, "saveClicked: creating weapon: " + newWeapon.toString());
        this.weaponDao.create(newWeapon);

        final ShootWeapon newShootWeapon = new ShootWeapon(-1, this.shootId, newWeapon.getId());
        Log.d(TAG, "saveClicked: creating shootWeapon: " + newShootWeapon.toString());
        this.shootWeaponDao.create(newShootWeapon);
    }

    // swaps listeners when unchecking radiobutton as you'd otherwise be able to check items in both groups
    private OnCheckedChangeListener lis1 = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            SoftInputHelper.hideSoftInput(getContext(), view);
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
    private OnCheckedChangeListener lis2 = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            SoftInputHelper.hideSoftInput(getContext(), view);
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

    // TODO: Find a way to please IntelliJ's Inspector so it stops complaining that this needs to be static
    private class getKnownDevice extends AsyncTask<String, Void, String> {
        private ArrayList<Weapon> weapons;

        @Override
        protected void onPreExecute() {
        }

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
        protected void onProgressUpdate(Void... values) {
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
                Log.d(TAG, "onPostExecute: added weapon with MAC: "  + weapons.get(i).getMac());
            }
        }
    }

}

