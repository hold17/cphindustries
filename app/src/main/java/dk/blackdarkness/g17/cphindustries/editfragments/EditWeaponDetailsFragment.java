package dk.blackdarkness.g17.cphindustries.editfragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.CallbackPopup;
import dk.blackdarkness.g17.cphindustries.recyclerview.PopupRecListAdapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class EditWeaponDetailsFragment extends Fragment implements View.OnClickListener, CallbackPopup {

    private static final String TAG = "DetailWeaponFragment";
    private TextView weaponNameTitle, weaponNameText, weaponIdText, weaponFiremodeText, weaponShootsText, statusText;
    private FloatingActionButton lock;
    private int sceneId;
    private int shootId;
    private Weapon weapon;
    private Button popupButton;
    private PopupWindow popupWindow;

    //private PopupRecListAdapter adapter;



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
        this.weapon = ApplicationConfig.getDaoFactory().getWeaponDao().getWeapon(weaponId);
        //this.popupWindow = popupWindow;




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
                onButtonShowPopup(getView());
                break;
            case R.id.popupCancel:
                popupWindow.dismiss();
                break;
        }

    }


    public void onButtonShowPopup(View view){

        // get a reference to the already created main layout


        ConstraintLayout relativeLayout = view.findViewById(R.id.weapon_details_edit);
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.edit_weapon_details_popup, null);
        System.out.println("Et egentligt ord 111" );

        TextView textTitle = popupView.findViewById(R.id.popupTitle);
        textTitle.setText(this.weapon.getName());


        // create the popup window
        createRecycler(popupView);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, 300, 470, focusable);



        // show the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        // TODO: button cancel
        //popupWindow.dismiss();


    }


    public void createRecycler(View view){
        System.out.println("Et egentligt ord 222" );
        ArrayList<Item> shoots = new ArrayList<>(ItemConverter.shootToItem(ApplicationConfig.getDaoFactory().getShootDao().getListByScene(sceneId)));
        RecyclerView recyclerView = view.findViewById(R.id.fr_editWeaponDetails_recyclerView);
        PopupRecListAdapter adapter = new PopupRecListAdapter(getContext(), shoots, this, this.weapon.getId());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onCheckClickSend(int shootId, boolean isChecked) {
        // DO ALL WITH THE SCENEID HER...


    }
   /* private static List<Item> getListOfShoots(int sceneId) {
        final List<Item> itemShoots = new ArrayList<>();
        final List<Shoot> shoots = ApplicationConfig.getDaoFactory().getShootDao().get(sceneId);

        for (Shoot s : shoots) {
            itemShoots.add(s);
        }
        System.out.println("Et egentligt ord" + itemShoots.toString());
        return itemShoots;
    }*/
}
