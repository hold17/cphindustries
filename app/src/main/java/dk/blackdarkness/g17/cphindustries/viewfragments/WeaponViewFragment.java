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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
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
    private int sceneId = -1;
    private int shootId = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weapon_view_layout, container, false);
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneId = getArguments().getInt("SCENE_ID");
        this.shootId = getArguments().getInt("SHOOT_ID");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weapons");
        lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_weapon_recyclerView);

//        Toast.makeText(getContext(), "Shoot ID = " + getArguments().getInt("SHOOT_ID"), Toast.LENGTH_LONG).show();
//        int sceneId = getArguments().getInt("SCENE_ID", -1);
//        System.out.println("SCENE ID = " + sceneId);
//        int shootId = getArguments().getInt("SHOOT_ID", -1);
//        System.out.println("SHOOT ID = " + shootId);

//        List<NavListItem> weapons = new ArrayList<>();
//        weapons.add(new NavListItem(new Weapon(0, "Weapon 1", FireMode.BURST, ConnectionStatus.NO_CONNECTION), false));
//        weapons.add(new NavListItem(new Weapon(1, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.BAR_0), false));
//        weapons.add(new NavListItem(new Weapon(2, "Weapon 3", FireMode.SINGLE, ConnectionStatus.BAR_3), false));
//        weapons.add(new NavListItem(new Weapon(3, "Weapon 4", ConnectionStatus.FULL), false)); // Default to SAFE mode
//        weapons.add(new NavListItem(new Weapon(4, "Weapon 5", FireMode.BURST, ConnectionStatus.BAR_1), false));

        final RecyclerViewClickListener listener = (v, position) -> goToDetailWeaponFragment(position);

//        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, weapons, listener);
        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, getListOfNavListItemsWithWeapons(sceneId, shootId), listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(false);
        SITHCallback.setSwipeEnabled(false);

        mItemTouchHelper = new ItemTouchHelper(SITHCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private static List<NavListItem> getListOfNavListItemsWithWeapons(int sceneId, int shootId) {
        final List<NavListItem> navListWeapons = new ArrayList<>();
        final List<Weapon> weapons = ApplicationConfig.getDaoFactory().getWeaponDao().get(sceneId, shootId);

        for (Weapon w : weapons) {
            navListWeapons.add(new NavListItem(w, false));
        }

        return navListWeapons;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditWeaponFragment.");
        goToEditWeaponFragment();
    }

    public void goToDetailWeaponFragment(int position) {
        Log.d(TAG, "goToDetailWeaponFragment: Returning");
        Fragment detailWeaponFragment = new DetailWeaponFragment();

        final Weapon chosenWeapon = ApplicationConfig.getDaoFactory().getWeaponDao().get(sceneId, shootId).get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("SCENE_ID", this.sceneId);
        bundle.putInt("SHOOT_ID", this.shootId);
        bundle.putInt("WEAPON_ID", chosenWeapon.getId());

        detailWeaponFragment.setArguments(bundle);

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
