package dk.blackdarkness.g17.cphindustries.editfragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class EditWeaponDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "DetailWeaponFragment";
    private TextView weaponNameTitle, weaponNameText, weaponIdText, weaponFiremodeText, weaponShootsText, statusText;
    private FloatingActionButton lock;
    private int sceneId;
    private int shootId;
    private Weapon weapon;
    private Button popupButton;
    private PopupWindow popupWindow;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_weapon_details, container, false);

        this.weaponNameTitle = view.findViewById(R.id.fr_weapon_details_edit_title);
        this.weaponNameText = view.findViewById(R.id.fr_weapon_details_edit_tvName_description);
        this.weaponShootsText = view.findViewById(R.id.fr_weapon_details_edit_tvShoot);

        this.statusText = view.findViewById(R.id.fr_weapon_details_edit_status_text);
        this.lock = view.findViewById(R.id.lockFab);
        this.popupButton = view.findViewById(R.id.fr_weapon_details_edit_popup);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneId = getArguments().getInt("SCENE_ID");
        this.shootId = getArguments().getInt("SHOOT_ID");
        final int weaponId = getArguments().getInt("WEAPON_ID");
        this.weapon = ApplicationConfig.getDaoFactory().getWeaponDao().get(sceneId, shootId, weaponId);
        this.popupWindow = popupWindow;




        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weapon Details");
//        this.statusText.setText("1: Device could not be connected. Make sure it is turned on and connected to the network.");
        this.weaponNameTitle.setText(this.weapon.getName());
        this.weaponNameText.setText(this.weapon.getName());
        //this.weaponIdText.setText("" + this.weapon.getId());
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
        this.popupButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_weapon_details_edit_popup:
                onButtonShowPopup(view);
                break;
        }

    }


    public void onButtonShowPopup(View view){

        // get a reference to the already created main layout
        ConstraintLayout relativeLayout = getView().findViewById(R.id.weapon_details_edit);
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup popupView = (ViewGroup) inflater.inflate(R.layout.edit_weapon_details_popup, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        System.out.println("sker der noget");


        // show the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        // dismiss the popup window



    }
}
