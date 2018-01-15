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
import android.widget.Toast;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateShotFragment;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;

import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_shot_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.recyclerView = this.view.findViewById(R.id.fr_editShot_recyclerView);

        this.sceneId = getArguments().getInt(ViewSceneActivity.SCENE_ID_KEY);

        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity)getActivity()).setActionBarTitle("Edit Shoots");
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);
        this.lock.setImageResource(R.drawable.ic_lock_open_white_24dp);
        this.lock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.openLockFabColor)));

        this.shoots = ItemConverter.shootToItem(this.shootDao.getListByScene(sceneId));

        final RecyclerViewClickListener listener = (v, position) -> System.out.println("STUFF");

        this.adapter = new EditRecListAdapter(getActivity(), this, shoots, listener);

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
        //Check if cache is cleared TODO: Work around empty lists!!!
        if(SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.CACHE_CLEARED)) {
            Toast.makeText(getContext(), "Cache has been cleared", Toast.LENGTH_SHORT).show();
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.CACHE_CLEARED);
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
        }
    }

    public void checkLock() {
        Log.d(TAG, "checkLock: Should save input data.");
        getActivity().onBackPressed();
    }
    public void goToCreateShotFragment() {
        Log.d(TAG, "goToCreateShotFragment: Returning.");
        Fragment createShotFragment = new CreateShotFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, this.sceneId);
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
