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
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateSceneFragment;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dto.Item;

import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;

public class EditSceneFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "EditSceneFragment";
    private FloatingActionButton add, lock;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerView recyclerView;
    private EditRecListAdapter adapter;
    private SceneDao sceneDao;
    private List<Item> scenes;
    private int selectedSceneId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_scene_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.recyclerView = this.view.findViewById(R.id.fr_editScene_recyclerView);

        SharedPreferenceManager.init(getContext());

        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity)getActivity()).setActionBarTitle("Edit Scenes");
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);
        this.lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
        this.lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));

        this.scenes = ItemConverter.sceneToItem(this.sceneDao.getList());

        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int itemId) {
                selectedSceneId = itemId;
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

        this.adapter = new EditRecListAdapter(getActivity(), this, scenes, listener);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
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
            this.scenes = ItemConverter.sceneToItem(this.sceneDao.getList());
            adapter.updateItems(this.scenes);
            adapter.notifyDataSetChanged();
        }
        Log.d(TAG, "Items onResume: " + adapter.getItemCount());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ViewSceneActivity)getActivity()).enableActionBar(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                checkLock();
                break;
            case R.id.createFab:
                goToCreateSceneFragment();
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
            Scene selectedScene = this.sceneDao.getScene(this.selectedSceneId);
            Log.d(TAG, "onClick: dao current name: " + selectedScene.getName());
            selectedScene.setName(name);
            this.sceneDao.update(selectedScene);
            Log.d(TAG, "onClick: dao new name: " + this.sceneDao.getScene(this.selectedSceneId).getName());
            this.selectedSceneId = -1;
        } else {
            getActivity().onBackPressed();
        }
    }

    public void goToCreateSceneFragment() {
        Log.d(TAG, "goToCreateSceneFragment: Returning");
        Fragment createSceneFragment = new CreateSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createSceneFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
