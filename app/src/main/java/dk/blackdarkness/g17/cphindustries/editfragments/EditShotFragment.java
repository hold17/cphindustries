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
import dk.blackdarkness.g17.cphindustries.createfragments.CreateShotFragment;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.helper.SoftInputHelper;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

public class EditShotFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "EditShotFragment";
    private FloatingActionButton lock, add;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView recyclerView;
    private EditRecListAdapter adapter;
    private int sceneId;
    private ShootDao shootDao;
    private List<Item> shoots;
    private int selectedShootId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_shot_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.recyclerView = this.view.findViewById(R.id.fr_editShot_recyclerView);

        SharedPreferenceManager.init(getContext());

        this.sceneId = getArguments().getInt(MainActivity.SCENE_ID_KEY);

        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Edit Shoots");

        view.setOnClickListener(this);

        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);
        this.lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
        this.lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));

        this.shoots = ItemConverter.shootToItem(this.shootDao.getListByScene(this.sceneId));

        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int itemId) {
                selectedShootId = itemId;
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

        this.adapter = new EditRecListAdapter(getActivity(), this, this.shoots, listener);

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
        //Check if cache is cleared TODO: Work around empty lists!!!
        boolean cacheIsCleared = SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.CACHE_CLEARED);
        boolean appIsReset = SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.APP_RESET);

        if(cacheIsCleared || appIsReset) {
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.CACHE_CLEARED);
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.APP_RESET);
            this.shoots = ItemConverter.shootToItem(this.shootDao.getListByScene(sceneId));
            adapter.updateItems(this.shoots);
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
                goToCreateShotFragment();
                break;
            case R.id.fr_editShot_layout:
                SoftInputHelper.hideSoftInput(getContext(), view);
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
            Shoot selectedShoot = this.shootDao.getShoot(this.selectedShootId);
            Log.d(TAG, "onClick: dao current name: " + selectedShoot.getName());
            selectedShoot.setName(name);
            this.shootDao.update(selectedShoot);
            Log.d(TAG, "onClick: dao new name: " + this.shootDao.getShoot(this.selectedShootId).getName());
            this.selectedShootId = -1;
        } else {
            getActivity().onBackPressed();
        }
    }

    public void goToCreateShotFragment() {
        Log.d(TAG, "goToCreateShotFragment: Returning.");
        Fragment createShotFragment = new CreateShotFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(MainActivity.SCENE_ID_KEY, this.sceneId);
        createShotFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createShotFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
