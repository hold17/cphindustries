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

import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponFragment;
import dk.blackdarkness.g17.cphindustries.entityfragments.DetailWeaponFragment;

import dk.blackdarkness.g17.cphindustries.recyclerview.NavListItem;
import dk.blackdarkness.g17.cphindustries.recyclerview.RecyclerListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

/**
 * Created by Thoma on 11/02/2017.
 */

public class WeaponViewFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "WeaponViewFragment";
    private FloatingActionButton lock;
    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weapon_view_layout, container, false);
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weapons");
        lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_weapon_recyclerView);

        List<NavListItem> weapons = new ArrayList<>();
        weapons.add(new NavListItem(new Weapon(0, "Weapon 1", FireMode.BURST, ConnectionStatus.NO_CONNECTION), false));
        weapons.add(new NavListItem(new Weapon(1, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.BAR_0), false));
        weapons.add(new NavListItem(new Weapon(2, "Weapon 3", FireMode.SINGLE, ConnectionStatus.BAR_3), false));
        weapons.add(new NavListItem(new Weapon(3, "Weapon 4", ConnectionStatus.FULL), false)); // Default to SAFE mode
        weapons.add(new NavListItem(new Weapon(4, "Weapon 5", FireMode.BURST, ConnectionStatus.BAR_1), false));

        final RecyclerViewClickListener listener = (v, position) -> goToDetailWeaponFragment();

        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, weapons, listener);
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
        Log.d(TAG, "onClick: lockFab. Returning EditWeaponFragment.");
        goToEditWeaponFragment();
    }

    public void goToDetailWeaponFragment() {
        Log.d(TAG, "goToDetailWeaponFragment: Returning");
        Fragment detailWeaponFragment = new DetailWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailWeaponFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToEditWeaponFragment() {
        Log.d(TAG, "goToEditWeaponFragment: Returning");
        Fragment editWeaponFragment = new EditWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editWeaponFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
