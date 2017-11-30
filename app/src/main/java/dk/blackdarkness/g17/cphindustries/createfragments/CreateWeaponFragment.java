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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import dk.blackdarkness.g17.cphindustries.R;

/**
 * Created by Thoma on 11/03/2017.
 */

public class CreateWeaponFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "CreateWeaponFragment";
    private View view;
    private TextView submitSave, submitCancel;
    private RadioGroup rgLeft, rgRight;
    private ConstraintLayout radioButtons, loading;

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
                break;
            case R.id.fr_create_weapon_tvCancel:
                getActivity().onBackPressed();
                break;
        }
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

        private ArrayList<String> weaponIpList;

        @Override
        protected String doInBackground(String... params) {

            try {
                // Fetch ip / mac addresses from nearby devices
                // Currently dummy data - use actual weapon DTO
                this.weaponIpList = new ArrayList<>();
                this.weaponIpList.add("123.123.123.123");
                this.weaponIpList.add("234.234.234.234");
                this.weaponIpList.add("345.345.345.345");
                this.weaponIpList.add("456.456.456.456");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            // Setup radio button view with found weapons
            int rgHalf = (weaponIpList.size()) / 2;
            final RadioButton[] rb = new RadioButton[weaponIpList.size()];
            for (int i = 0; i < weaponIpList.size(); i++) {
                if (i <= rgHalf - 1) {
                    rb[i] = new RadioButton(getContext());
                    rb[i].setText(weaponIpList.get(i));
                    rb[i].setId(i + 100);
                    rgLeft.addView(rb[i]);
                } else {
                    rb[i] = new RadioButton(getContext());
                    rb[i].setText(weaponIpList.get(i));
                    rb[i].setId(i + 100);
                    rgRight.addView(rb[i]);
                }
                radioButtons.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                Log.d(TAG, "onPostExecute: " + weaponIpList.get(i));
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

