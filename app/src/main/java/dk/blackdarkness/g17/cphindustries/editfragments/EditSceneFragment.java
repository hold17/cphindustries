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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        final RecyclerViewClickListener listener = (v, position) -> {
            String name = this.adapter.getEditTextString(position);
            int id = this.scenes.get(position).getId();
            Log.d(TAG, "onClick: local current name: " + this.scenes.get(position).getName());
            Log.d(TAG, "onClick: dao current name: " + this.sceneDao.getScene(id).getName());
            this.scenes.get(position).setName(name);
            this.sceneDao.update((Scene) this.scenes.get(position));
            Log.d(TAG, "onClick: local new name: " + this.scenes.get(position).getName());
            Log.d(TAG, "onClick: dao new name: " + this.sceneDao.getScene(id).getName());
        };

        this.adapter = new EditRecListAdapter(getActivity(), this, scenes, listener);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(true);
        SITHCallback.setSwipeEnabled(true);

        mItemTouchHelper = new ItemTouchHelper(SITHCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();

        //TODO: Work around empty lists!!!
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
        Log.d(TAG, "checkLock: Should save input data.");
        getActivity().onBackPressed();
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
