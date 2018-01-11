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
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateWeaponFragment;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.EditRecListAdapter;
//import dk.blackdarkness.g17.cphindustries.recyclerview.NavListItem;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

public class EditWeaponFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "EditWeaponFragment";
    private FloatingActionButton lock, add;
    private ItemTouchHelper mItemTouchHelper;
    private int sceneId, shootId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_weapon_layout, container, false);
        this.lock = view.findViewById(R.id.lockFab);
        this.add = view.findViewById(R.id.createFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneId = getArguments().getInt(SceneViewActivity.SCENE_ID_KEY);
        this.shootId = getArguments().getInt(SceneViewActivity.SHOOT_ID_KEY);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity)getActivity()).setActionBarTitle("Edit Weapons");
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_editWeapon_recyclerView);

//        List<Item> weapons = new ArrayList<>();
//        weapons.add(new Weapon(0, "Weapon 1", FireMode.BURST, ConnectionStatus.NO_CONNECTION));
//        weapons.add(new Weapon(1, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.BAR_0));
//        weapons.add(new Weapon(2, "Weapon 3", FireMode.SINGLE, ConnectionStatus.BAR_3));
//        weapons.add(new Weapon(3, "Weapon 4", ConnectionStatus.FULL)); // Default to SAFE mode
//        weapons.add(new Weapon(4, "Weapon 5", FireMode.BURST, ConnectionStatus.BAR_1));
        List<Item> weapons = ItemConverter.weaponToItem(ApplicationConfig.getDaoFactory().getWeaponDao().get(sceneId, shootId));

        final RecyclerViewClickListener listener = (v, position) -> System.out.println("STUFF");

        EditRecListAdapter adapter = new EditRecListAdapter(getActivity(), this, weapons, listener);
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
                goToCreateWeaponFragment();
                break;
        }
    }

    public void checkLock() {
            //Skal erstattes med save data
            Log.d(TAG, "checkLock: Lock pressed. Should save input data.");
            getActivity().onBackPressed();
    }

    public void goToCreateWeaponFragment() {
        Log.d(TAG, "goToCreateWeaponFragment: Returning.");
        Fragment createWeaponFragment = new CreateWeaponFragment();

        createWeaponFragment.setArguments(getArguments());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createWeaponFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
