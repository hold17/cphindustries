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
import dk.blackdarkness.g17.cphindustries.createfragments.CreateShotFragment;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

import dk.blackdarkness.g17.cphindustries.recyclerview.NavListItem;
import dk.blackdarkness.g17.cphindustries.recyclerview.RecyclerListAdapter;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_shot_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Edit Shot");
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_editShot_recyclerView);

        List<NavListItem> shoots = new ArrayList<>();
        shoots.add(new NavListItem(new Shoot(0, "Shoot 1"), true));
        shoots.add(new NavListItem(new Shoot(1, "Shoot 2"), true));
        shoots.add(new NavListItem(new Shoot(2, "Shoot 3"), true));

        final RecyclerViewClickListener listener = (v, position) -> System.out.println("STUFF");

        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, shoots, listener);
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
