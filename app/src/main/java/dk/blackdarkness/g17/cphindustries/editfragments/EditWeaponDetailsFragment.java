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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.entityfragments.DetailWeaponFragment;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.CallbackPopup;
import dk.blackdarkness.g17.cphindustries.recyclerview.PopupRecListAdapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class EditWeaponDetailsFragment extends Fragment implements View.OnClickListener, CallbackPopup {

    private static final String TAG = "DetailWeaponFragment";
    private TextView weaponNameTitle;
    private EditText weaponEdit;
    private FloatingActionButton lock;
    private Weapon weapon;
    private Button popupButton;
    private View popupView;
    private PopupWindow popupWindow;
    private ArrayList<Integer> shootIdList = new ArrayList<>();
    private ArrayList<Boolean> weaponIdList = new ArrayList<>();
    private int bundleShoot, bundleScene;


    //private PopupRecListAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_weapon_details, container, false);

        this.weaponNameTitle = view.findViewById(R.id.fr_weapon_details_edit_title);
        this.lock = view.findViewById(R.id.lockFab);
        this.popupButton = view.findViewById(R.id.fr_weapon_details_edit_popup);
        Log.d(TAG, "onCreateView: Returning.");

        final int weaponId = getArguments().getInt("WEAPON_ID");
        this.weapon = ApplicationConfig.getDaoFactory().getWeaponDao().getWeapon(weaponId);
        this.weaponEdit = view.findViewById(R.id.fr_weapon_details_edit_tvName_description);
        this.bundleScene = getArguments().getInt(ViewSceneActivity.SCENE_ID_KEY);
        this.bundleShoot = getArguments().getInt(ViewSceneActivity.SHOOT_ID_KEY);


        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weapon Details");
        this.weaponNameTitle.setText(this.weapon.getName());
        this.weaponEdit.setText(this.weapon.getName());


        this.lock.setOnClickListener(this);
        this.popupButton.setOnClickListener(this);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        this.popupView = inflater.inflate(R.layout.edit_weapon_details_popup, null);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_weapon_details_edit_popup:
                onButtonShowPopup(getView());
                break;
            case R.id.popupCancel:
                cancelChanges();
                break;
            case R.id.popupApply:
                applyChanges();
                popupWindow.dismiss();
                break;
            case R.id.lockFab:
                //Edit view should be different from the one navigated to
                //from ViewWeaponFragment. Edit this one weapon only?
                Log.d(TAG, "onClick: Trying to open edit weapon fragment.");

                goToWeaponDetailsFragment();
                break;
        }

    }

    private void goToWeaponDetailsFragment() {
        Log.d(TAG, "goToEditWeaponFragment: Returning");
        weapon.setName(weaponEdit.getText().toString());
        ApplicationConfig.getDaoFactory().getWeaponDao().update(weapon);
        Fragment detailWeaponFragment = new DetailWeaponFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, this.bundleScene);
        bundle.putInt(ViewSceneActivity.SHOOT_ID_KEY, this.bundleShoot);
        bundle.putInt(ViewSceneActivity.WEAPON_ID_KEY, weapon.getId());
        detailWeaponFragment.setArguments(bundle);


        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailWeaponFragment)
                .addToBackStack(null)
                .commit();
    }


    public void onButtonShowPopup(View view) {

        // get a reference to the already created main layout


        ConstraintLayout relativeLayout = view.findViewById(R.id.weapon_details_edit);
        // inflate the layout of the popup window

        System.out.println("Et egentligt ord 111");

        TextView textTitle = popupView.findViewById(R.id.popupTitle);
        textTitle.setText(this.weapon.getName());

        // create the popup window
        createRecycler(popupView);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView, 300, 470, focusable);
        Button cancelButton = popupView.findViewById(R.id.popupCancel);
        Button applyButton = popupView.findViewById(R.id.popupApply);
        cancelButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);


        // show the popup window
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        // TODO: button cancel

        //popupWindow.dismiss();


    }


    public void createRecycler(View view) {
        System.out.println("Et egentligt ord 222");
        ArrayList<Item> shoots = new ArrayList<>(ItemConverter.shootToItem(ApplicationConfig.getDaoFactory().getShootDao().getList()));
        RecyclerView recyclerView = view.findViewById(R.id.fr_editWeaponDetails_recyclerView);
        PopupRecListAdapter adapter = new PopupRecListAdapter(getContext(), shoots, this, this.weapon.getId());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onCheckClickSend(int shootId, boolean isChecked) {
        // DO ALL WITH THE SCENEID HER...
        this.weaponIdList.add(isChecked);
        this.shootIdList.add(shootId);

    }

    public void applyChanges() {

        for (int i = 0; i < shootIdList.size(); i++) {

            if (weaponIdList.get(i)) {
                ShootWeapon sw = new ShootWeapon(shootIdList.get(i), this.weapon.getId());
                ApplicationConfig.getDaoFactory().getShootWeaponDao().create(sw);
                System.out.println(Integer.toString(shootIdList.get(i)) + "hvad så man");
            } else {
                ApplicationConfig.getDaoFactory().getShootWeaponDao().delete(shootIdList.get(i), this.weapon.getId());
                System.out.println("whatthe");
            }
        }
        weaponIdList.clear();
        shootIdList.clear();
    }

    public void cancelChanges() {
        weaponIdList.clear();
        shootIdList.clear();
        popupWindow.dismiss();

    }
}
