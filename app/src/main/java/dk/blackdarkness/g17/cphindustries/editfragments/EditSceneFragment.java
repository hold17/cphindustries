package dk.blackdarkness.g17.cphindustries.editfragments;

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

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.NavListItem;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.RecyclerListAdapter;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateSceneFragment;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.helper.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.helper.SimpleItemTouchHelperCallback;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditSceneFragment extends Fragment implements View.OnClickListener, OnStartDragListener {

    private static final String TAG = "EditSceneFragment";

    private View view;
    private FloatingActionButton add, lock;
    private Fragment createSceneFragment;

    private RecyclerView recyclerView;
    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_scene_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Edit Scene");
        ((SceneViewActivity)getActivity()).resetActionBar(true);
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);

        // Initiate list view
        this.recyclerView = this.view.findViewById(R.id.fr_editScene_recyclerView);

        List<NavListItem> scenes = new ArrayList<NavListItem>();
        scenes.add(new NavListItem(new Scene(1, "1 - The shooting scene"), true));
        scenes.add(new NavListItem(new Scene(22, "22 - Robbing the Bank"), true));
        scenes.add(new NavListItem(new Scene(53, "54 - The escape"), true));

        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, scenes);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(true);
        SITHCallback.setSwipeEnabled(true);

        mItemTouchHelper = new ItemTouchHelper(SITHCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
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
        createSceneFragment = new CreateSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.scene_fragment_container, createSceneFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
