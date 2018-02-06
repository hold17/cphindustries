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
import dk.blackdarkness.g17.cphindustries.createfragments.CreateFragment;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateWeaponFragment;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.helper.FragmentType;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.helper.SoftInputHelper;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

public class EditFragment  extends Fragment implements View.OnClickListener, OnStartDragListener {
    private FragmentType FRAGMENT_TYPE;
    private static final String TAG = "EditFragment";
    private View view;
    private FloatingActionButton lock, add;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView recyclerView;
    private EditRecListAdapter adapter;
    private SceneDao sceneDao;
    private ShootDao shootDao;
    private WeaponDao weaponDao;
    private List<Item> items;
    private int sceneId;
    private int shootId;
    private int selectedItemId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container, false);
        lock = view.findViewById(R.id.lockFab);
        add = view.findViewById(R.id.createFab);
        FRAGMENT_TYPE = (FragmentType) getArguments().getSerializable(MainActivity.FRAGMENT_TYPE_KEY);

        if (savedInstanceState != null)
            Log.d(TAG, "onCreateView: savedInstanceState contents: " + savedInstanceState.toString());
        Log.d(TAG, "onCreateView: bundle contents: " + getArguments().toString());

        Log.d(TAG, FRAGMENT_TYPE + " onCreateView: Returning.");

        recyclerView = view.findViewById(R.id.fr_edit_recyclerView);

        SharedPreferenceManager.init(getContext());

        // only set the ones needed throws AssertException if FRAGMENT_TYPE is null
        assert FRAGMENT_TYPE != null;
        switch (FRAGMENT_TYPE) {
            case SHOOTS:
                sceneId = getArguments().getInt(MainActivity.SCENE_ID_KEY);
                shootDao = ApplicationConfig.getDaoFactory().getShootDao();
                break;
            case WEAPONS:
                shootId = getArguments().getInt(MainActivity.SHOOT_ID_KEY);
                weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
                break;
            default:
                sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();
                break;
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Edit " + FRAGMENT_TYPE.getTypeAsString(getContext(), 2));
        // SCENES is the top level, so no subtitle
        if (FRAGMENT_TYPE != FragmentType.SCENES)
            ((MainActivity)getActivity()).setActionBarSubtitle(getArguments().getString(MainActivity.SUBTITLE_KEY));
        else ((MainActivity)getActivity()).setActionBarSubtitle("");

        view.setOnClickListener(this);

        add.setVisibility(View.VISIBLE);
        add.setOnClickListener(this);
        lock.setOnClickListener(this);
        lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
        lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));

        // ideally there should not be an item converter for each item type and instead the dao should just always return items
        switch (FRAGMENT_TYPE) {
            case SCENES: items = ItemConverter.sceneToItem(sceneDao.getList()); break;
            case SHOOTS: items = ItemConverter.shootToItem(shootDao.getListByScene(sceneId)); break;
            case WEAPONS: items = ItemConverter.weaponToItem(weaponDao.getListByShoot(shootId)); break;
        }

        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int itemId) {
                selectedItemId = itemId;
                if (adapter.isEditingText) {
                    add.setVisibility(View.GONE);
                    lock.setImageResource(R.drawable.ic_check_white_24dp);
                    lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPositive)));
                } else {
                    add.setVisibility(View.VISIBLE);
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

        adapter = new EditRecListAdapter(getActivity(), this, items, listener);

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
            // ideally there should not be an item converter for each item type and instead the dao should just always return items
            switch (FRAGMENT_TYPE) {
                case SCENES: items = ItemConverter.sceneToItem(sceneDao.getList()); break;
                case SHOOTS: items = ItemConverter.shootToItem(shootDao.getListByScene(sceneId)); break;
                case WEAPONS: items = ItemConverter.weaponToItem(weaponDao.getListByShoot(shootId)); break;
            }
            adapter.updateItems(items);
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
                goToCreateFragment();
                break;
            case R.id.fr_edit_layout:
                SoftInputHelper.hideSoftInput(getContext(), view);
                break;
        }
    }

    public void checkLock() {
        // users believe pressing the lock a second time is what saves changes, the user is always right
        if (adapter.isEditingText) {
            Log.d(TAG, "checkLock: Should save input data.");
            String name = adapter.setNewItemTextAndReturnText();
            // This will be simplified a lot once the daos are refactored to only deal with items
            switch (FRAGMENT_TYPE) {
                case SCENES:
                    Scene selectedScene = sceneDao.getScene(selectedItemId);
                    Log.d(TAG, "onClick: dao current name: " + selectedScene.getName());
                    selectedScene.setName(name);
                    sceneDao.update(selectedScene);
                    Log.d(TAG, "onClick: dao new name: " + sceneDao.getScene(selectedItemId).getName());
                    break;
                case SHOOTS:
                    Shoot selectedShoot = shootDao.getShoot(selectedItemId);
                    Log.d(TAG, "onClick: dao current name: " + selectedShoot.getName());
                    selectedShoot.setName(name);
                    shootDao.update(selectedShoot);
                    Log.d(TAG, "onClick: dao new name: " + shootDao.getShoot(selectedItemId).getName());
                    break;
                case WEAPONS:
                    Weapon selectedWeapon = weaponDao.getWeapon(selectedItemId);
                    Log.d(TAG, "onClick: dao current name: " + selectedWeapon.getName());
                    selectedWeapon.setName(name);
                    weaponDao.update(selectedWeapon);
                    Log.d(TAG, "onClick: dao new name: " + weaponDao.getWeapon(selectedItemId).getName());
                    break;
            }
            add.setVisibility(View.VISIBLE);
            lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
            lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));
            selectedItemId = -1;
        } else {
            getActivity().onBackPressed();
        }
    }

    public void goToCreateFragment() {
        Log.d(TAG, "goToCreateFragment: Returning");
        ((MainActivity)getActivity()).enableActionBar(true);
        Fragment createFragment;

        // Make subtitle and bundle for next view fragment
        Bundle bundle = new Bundle();
        if (FRAGMENT_TYPE != FragmentType.WEAPONS) {
            if (FRAGMENT_TYPE == FragmentType.SCENES) {
                bundle.putSerializable(MainActivity.FRAGMENT_TYPE_KEY, FragmentType.SCENES);
            }
            else if (FRAGMENT_TYPE == FragmentType.SHOOTS) {
                bundle.putInt(MainActivity.SCENE_ID_KEY, sceneId);
                bundle.putSerializable(MainActivity.FRAGMENT_TYPE_KEY, FragmentType.SHOOTS);
            }
            // use consolidated create fragment here
            createFragment = new CreateFragment();
        } else {
            bundle.putInt(MainActivity.SCENE_ID_KEY, sceneId);
            bundle.putInt(MainActivity.SHOOT_ID_KEY, shootId);
            createFragment = new CreateWeaponFragment();
        }
        createFragment.setArguments(bundle);
        Log.d(TAG, "goToViewFragment: bundle contents: " + bundle.toString());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
