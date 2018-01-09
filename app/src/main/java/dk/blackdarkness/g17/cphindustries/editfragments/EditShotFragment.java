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

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateShotFragment;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditShotFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "EditShotFragment";
    private FloatingActionButton lock, add;
    private ItemTouchHelper mItemTouchHelper;
    private int sceneId; // TODO: HARDCODED for now...

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_shot_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneId = getArguments().getInt(SceneViewActivity.SCENE_ID_KEY);
        System.out.println("SCENE ID: " + this.sceneId);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SceneViewActivity)getActivity()).setActionBarTitle("Edit Shoots");
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_editShot_recyclerView);

        final RecyclerViewClickListener listener = (v, position) -> System.out.println("STUFF");

        final List<Item> shoots = ItemConverter.shootToItem(ApplicationConfig.getDaoFactory().getShootDao().get(sceneId));

        EditRecListAdapter adapter = new EditRecListAdapter(getActivity(), this, shoots, listener);
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
        bundle.putInt("sceneId", this.sceneId);
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
