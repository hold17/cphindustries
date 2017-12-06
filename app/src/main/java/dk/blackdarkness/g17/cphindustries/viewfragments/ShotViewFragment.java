package dk.blackdarkness.g17.cphindustries.viewfragments;

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
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.editfragments.EditShotFragment;

import dk.blackdarkness.g17.cphindustries.recyclerview.NavListItem;
import dk.blackdarkness.g17.cphindustries.recyclerview.RecyclerListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

/**
 * Created by Thoma on 11/02/2017.
 */

public class ShotViewFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "ShotViewFragment";
    private FloatingActionButton lock;
    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_shot_view_layout, container, false);
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Shots");
        lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_shot_recyclerView);

        List<NavListItem> shoots = new ArrayList<>();
        shoots.add(new NavListItem(new Shoot(0, "Shoot 1"), false));
        shoots.add(new NavListItem(new Shoot(1, "Shoot 2"), false));
        shoots.add(new NavListItem(new Shoot(2, "Shoot 3"), false));

        final RecyclerViewClickListener listener = (v, position) -> goToWeaponViewFragment();

        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, shoots, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(false);
        SITHCallback.setSwipeEnabled(false);

        mItemTouchHelper = new ItemTouchHelper(SITHCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditShotFragment.");
        goToEditShotFragment();
    }

    public void goToEditShotFragment() {
        Log.d(TAG, "goToEditShotFragment: Returning");
        Fragment editShotFragment = new EditShotFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editShotFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToWeaponViewFragment() {
        Log.d(TAG, "goToWeaponViewFragment: Returning");
        Fragment weaponViewFragment = new WeaponViewFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weaponViewFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
