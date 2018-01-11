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
import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

public class CreateWeaponFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "CreateWeaponFragment";
    private View view;
    private TextView submitSave, submitCancel;
    private RadioGroup rgLeft, rgRight;
    private ConstraintLayout radioButtons, loading;
    private EditText weaponNameText;

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

        this.weaponNameText = view.findViewById(R.id.fr_create_weapon_editText);

        initLayout();
        Log.d(TAG, "onCreateView: Returning");
        return view;
    }

    public void initLayout() {
        getActivity().setTitle("Create Weapon");

        radioButtons.setVisibility(View.GONE);

        submitSave.setOnClickListener(this);
        submitSave.setText("Submit");

        submitCancel.setOnClickListener(this);
        submitCancel.setText("Cancel");

        rgLeft.setOnCheckedChangeListener(lis1);
        rgRight.setOnCheckedChangeListener(lis2);

        new DeviceLocation().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_create_weapon_tvSave:
                getActivity().onBackPressed();
                saveClicked();
                break;
            case R.id.fr_create_weapon_tvCancel:
                getActivity().onBackPressed();
                break;
        }
    }

    private void saveClicked() {

    }

    private RadioGroup.OnCheckedChangeListener lis1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rgRight.setOnCheckedChangeListener(null);
                rgRight.clearCheck();
                rgRight.setOnCheckedChangeListener(lis2);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener lis2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rgLeft.setOnCheckedChangeListener(null);
                rgLeft.clearCheck();
                rgLeft.setOnCheckedChangeListener(lis1);
            }
        }
    };

    private class DeviceLocation extends AsyncTask<String, Void, String> {

        private ArrayList<Weapon> weapons;

        @Override
        protected String doInBackground(String... params) {

            try {
                // Fetch ip / mac addresses from nearby devices
                // Currently dummy data - use actual weapon DTO
                this.weapons = new ArrayList<>();
                this.weapons.add(new Weapon(1, ConnectionStatus.FULL, "127.0.0.1", "00:00:00:00:00:00"));
                this.weapons.add(new Weapon(1, ConnectionStatus.BAR_1, "127.0.0.2", "00:00:00:00:00:01"));
                this.weapons.add(new Weapon(1, ConnectionStatus.BAR_3, "127.0.0.3", "00:00:00:00:00:02"));
                this.weapons.add(new Weapon(1, ConnectionStatus.FULL, "127.0.0.4", "00:00:00:00:00:04"));
                this.weapons.add(new Weapon(1, ConnectionStatus.FULL, "127.0.0.4", "00:00:00:00:00:05"));
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
                    rb[i].setId(i + 100);
                    rgRight.addView(rb[i]);
                } else {
                    rb[i] = new RadioButton(getContext());
                    rb[i].setText(weapons.get(i).getMac());
                    rb[i].setId(i + 100);
                    rgLeft.addView(rb[i]);
                }
                radioButtons.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                Log.d(TAG, "onPostExecute: " + weapons.get(i).getMac());
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

