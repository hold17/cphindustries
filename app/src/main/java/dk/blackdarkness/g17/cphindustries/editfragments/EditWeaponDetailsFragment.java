package dk.blackdarkness.g17.cphindustries.editfragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootWeaponDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.ShootWeapon;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.PopupRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.PopupCallback;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class EditWeaponDetailsFragment extends Fragment implements View.OnClickListener, PopupCallback {
    private static final String TAG = "EditWeaponDetailsFrag";
    private TextView weaponNameTitle;
    private EditText weaponEdit;
    private FloatingActionButton lock;
    private WeaponDao weaponDao;
    private ShootWeaponDao shootWeaponDao;
    private ShootDao shootDao;
    private Weapon weapon;
    private Button popupButton;
    private PopupWindow popupWindow;
    private List<Integer> changedShootIds = new ArrayList<>();
    private List<Item> shoots;
    private int sceneId, shootId, weaponId, recyclerHeight;

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
        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();
        this.shootWeaponDao = ApplicationConfig.getDaoFactory().getShootWeaponDao();

        this.weapon = this.weaponDao.getWeapon(this.weaponId);
        this.shoots = ItemConverter.shootToItem(this.shootDao.getList());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weapon Details");

        this.weaponNameTitle.setText(this.weapon.getName());
        this.weaponEdit.setText(this.weapon.getName());

        this.lock.setOnClickListener(this);
        this.lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
        this.lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));
        this.popupButton.setOnClickListener(this);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.recyclerHeight = (displaymetrics.heightPixels * 72) / 100;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fr_weapon_details_edit_popup:
                onButtonShowPopup(getView());
                break;
            case R.id.fr_editWeaponDetails_popupCancel:
                changedShootIds.clear();
                popupWindow.dismiss();
                break;
            case R.id.fr_editWeaponDetails_popupApply:
                applyChanges();
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
        RecyclerView recyclerView = view.findViewById(R.id.fr_editWeaponDetails_recyclerView);
        PopupRecListAdapter adapter = new PopupRecListAdapter(getContext(), this.shoots, this, this.weapon.getId());
        recyclerView.getLayoutParams().height = recyclerHeight;

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onCheckClickSend(int shootId) {
        if (this.changedShootIds.contains(shootId)) {
            Log.d(TAG, "onCheckClickSend: removed from list: " + this.shootDao.getShoot(shootId).getName());
            this.changedShootIds.remove((Integer) shootId);
        } else {
            Log.d(TAG, "onCheckClickSend: added to list: " + this.shootDao.getShoot(shootId).getName());
            this.changedShootIds.add(shootId);
        }
    }

    private void applyChanges() {
        for (int shootId: changedShootIds) {
            if (shootWeaponDao.getShootWeapon(shootId, this.weaponId) == null) {
                ShootWeapon sw = new ShootWeapon(shootId, this.weaponId);
                this.shootWeaponDao.create(sw);
                Log.d(TAG, "applyChanges: creating new ShootWeapon with shootId: " + shootId);
            } else {
                Log.d(TAG, "applyChanges: deleting ShootWeapon with shootId: " + shootId);
                this.shootWeaponDao.delete(shootId, this.weaponId);
            }
        }
        changedShootIds.clear();
        popupWindow.dismiss();
    }
}
