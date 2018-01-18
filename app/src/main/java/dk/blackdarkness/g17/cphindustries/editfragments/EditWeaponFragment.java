package dk.blackdarkness.g17.cphindustries.editfragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.MainActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateWeaponFragment;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

public class EditWeaponFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "EditWeaponFragment";
    private FloatingActionButton lock, add;
    private ItemTouchHelper mItemTouchHelper;
    private int sceneId;
    private int shootId;
    private RecyclerView recyclerView;
    private EditRecListAdapter adapter;
    private WeaponDao weaponDao;
    private List<Item> weapons;
    private int selectedWeaponId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_weapon_layout, container, false);
        this.lock = view.findViewById(R.id.lockFab);
        this.add = view.findViewById(R.id.createFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.recyclerView = this.view.findViewById(R.id.fr_editWeapon_recyclerView);

        SharedPreferenceManager.init(getContext());

        this.sceneId = getArguments().getInt(MainActivity.SCENE_ID_KEY);
        this.shootId = getArguments().getInt(MainActivity.SHOOT_ID_KEY);

        this.weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Edit Weapons");
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);
        this.lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
        this.lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));

        this.weapons = ItemConverter.weaponToItem(this.weaponDao.getListByShoot(shootId));

        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int itemId) {
                selectedWeaponId = itemId;
                if (adapter.isEditingText) {
                    lock.setImageResource(R.drawable.ic_check_white_24dp);
                    lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPositive)));
                } else {
                    lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
                    lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));
                }
            }

            @Override
            public boolean onLongClick(View view, int itemId) {
                // do nothing
                return false;
            }
        };

        this.adapter = new EditRecListAdapter(getActivity(), this, weapons, listener);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(getContext(), adapter);
        SITHCallback.setLongPressDragEnabled(false);
        SITHCallback.setSwipeEnabled(true);

        mItemTouchHelper = new ItemTouchHelper(SITHCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean cacheIsCleared = SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.CACHE_CLEARED);
        boolean appIsReset = SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.APP_RESET);

        if(cacheIsCleared || appIsReset) {
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.CACHE_CLEARED);
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.APP_RESET);
            this.weapons = ItemConverter.weaponToItem(this.weaponDao.getListByShoot(shootId));
            adapter.updateItems(this.weapons);
            adapter.notifyDataSetChanged();
        }
        Log.d(TAG, "Items onResume: " + adapter.getItemCount());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                checkLock();
                break;
            case R.id.createFab:
                goToCreateWeaponFragment();
                break;
        }
    }

    public void checkLock() {
        // users believe pressing the lock a second time is what saves changes, the user is always right
        if (this.adapter.isEditingText) {
            Log.d(TAG, "checkLock: Should save input data.");
            this.lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
            this.lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));
            String name = this.adapter.setNewItemTextAndReturnText();
            Weapon selectedWeapon = this.weaponDao.getWeapon(this.selectedWeaponId);
            Log.d(TAG, "onClick: dao current name: " + selectedWeapon.getName());
            selectedWeapon.setName(name);
            this.weaponDao.update(selectedWeapon);
            Log.d(TAG, "onClick: dao new name: " + this.weaponDao.getWeapon(this.selectedWeaponId).getName());
            this.selectedWeaponId = -1;
        } else {
            getActivity().onBackPressed();
        }
    }

    public void goToCreateWeaponFragment() {
        Log.d(TAG, "goToCreateWeaponFragment: Returning.");
        Fragment createWeaponFragment = new CreateWeaponFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.SCENE_ID_KEY, this.sceneId);
        bundle.putInt(MainActivity.SHOOT_ID_KEY, this.shootId);
        createWeaponFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createWeaponFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
