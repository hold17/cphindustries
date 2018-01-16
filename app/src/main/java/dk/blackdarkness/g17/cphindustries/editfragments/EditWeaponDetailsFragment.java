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
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootWeaponDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.entityfragments.DetailWeaponFragment;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.PopupCallback;
import dk.blackdarkness.g17.cphindustries.recyclerview.PopupRecListAdapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class EditWeaponDetailsFragment extends Fragment implements View.OnClickListener, PopupCallback {
    private static final String TAG = "EditWeaponDetailsFrag";
    private TextView weaponNameTitle;
    private EditText weaponEdit;
    private FloatingActionButton lock;
    private WeaponDao weaponDao;
    private ShootWeaponDao shootWeaponDao;
    private Weapon weapon;
    private Button popupButton;
    private PopupWindow popupWindow;
    private ArrayList<Integer> shootIdList = new ArrayList<>();
    private ArrayList<Boolean> weaponIdList = new ArrayList<>();
    private int sceneId, shootId, weaponId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_weapon_details, container, false);
        Log.d(TAG, "onCreateView: Returning.");

        this.lock = view.findViewById(R.id.lockFab);
        this.weaponNameTitle = view.findViewById(R.id.fr_weapon_details_edit_title);
        this.weaponEdit = view.findViewById(R.id.fr_weapon_details_edit_tvName_description);
        this.popupButton = view.findViewById(R.id.fr_weapon_details_edit_popup);

        this.sceneId = getArguments().getInt(ViewSceneActivity.SCENE_ID_KEY);
        this.shootId = getArguments().getInt(ViewSceneActivity.SHOOT_ID_KEY);
        this.weaponId = getArguments().getInt(ViewSceneActivity.WEAPON_ID_KEY);

        this.weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
        this.shootWeaponDao = ApplicationConfig.getDaoFactory().getShootWeaponDao();

        this.weapon = this.weaponDao.getWeapon(this.weaponId);

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_weapon_details_edit_popup:
                onButtonShowPopup(getView());
                break;
            case R.id.fr_editWeaponDetails_popupCancel:
                cancelChanges();
                break;
            case R.id.fr_editWeaponDetails_popupApply:
                applyChanges();
                popupWindow.dismiss();
                break;
            case R.id.lockFab:
                Log.d(TAG, "onClick: Going to weapon details fragment.");
                weapon.setName(weaponEdit.getText().toString());
                this.weaponDao.update(weapon);
                getActivity().onBackPressed();
                break;
        }
    }

    private void onButtonShowPopup(View view) {
        View popupView;
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.edit_weapon_details_popup, null);

        // get a reference to the already created main layout
        ConstraintLayout constraintLayout = view.findViewById(R.id.weapon_details_edit);

        TextView textTitle = popupView.findViewById(R.id.fr_editWeaponDetails_title);
        textTitle.setText(this.weapon.getName());

        Button cancelButton = popupView.findViewById(R.id.fr_editWeaponDetails_popupCancel);
        Button applyButton = popupView.findViewById(R.id.fr_editWeaponDetails_popupApply);

        cancelButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);

        // create RecyclerView attached to popup
        createRecycler(popupView);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        popupWindow = new PopupWindow(popupView, width, height, true);

        // set shadow effect
        popupWindow.setElevation(20);

        // show the popup window
        popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
    }

    private void createRecycler(View view) {
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

    private void applyChanges() {
        for (int i = 0; i < shootIdList.size(); i++) {
            if (weaponIdList.get(i)) {
                ShootWeapon sw = new ShootWeapon(shootIdList.get(i), this.weapon.getId());
                this.shootWeaponDao.create(sw);
                System.out.println(Integer.toString(shootIdList.get(i)) + "hvad så man");
            } else {
                this.shootWeaponDao.delete(shootIdList.get(i), this.weapon.getId());
                System.out.println("whatthe");
            }
        }
        weaponIdList.clear();
        shootIdList.clear();
    }

    private void cancelChanges() {
        weaponIdList.clear();
        shootIdList.clear();
        popupWindow.dismiss();
    }
}
