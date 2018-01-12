package dk.blackdarkness.g17.cphindustries.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponFragment;
import dk.blackdarkness.g17.cphindustries.entityfragments.DetailWeaponFragment;

import dk.blackdarkness.g17.cphindustries.helper.BreadcrumbHelper;
import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

public class ViewWeaponFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "ViewWeaponFragment";
    private FloatingActionButton lock;
    private WeaponDao weaponDao;
    private ShootDao shootDao;
    private SceneDao sceneDao;
    private int sceneId;
    private int shootId;
    private StdRecListAdapter adapter;

    private List<Item> weapons;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weapon_view_layout, container, false);
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneId = getArguments().getInt(ViewSceneActivity.SCENE_ID_KEY);
        this.shootId = getArguments().getInt(ViewSceneActivity.SHOOT_ID_KEY);

        this.weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();
        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity)getActivity()).setActionBarTitle("Weapons");
        ((ViewSceneActivity)getActivity()).setActionBarSubtitle(BreadcrumbHelper.getSubtitle(sceneDao.get(sceneId), shootDao.get(shootId)));
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

        this.weapons = getListOfWeapons(sceneId, shootId);

        final RecyclerViewClickListener listener = (v, position) -> goToDetailWeaponFragment(position);

        adapter = new StdRecListAdapter(getActivity(), this.weapons, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(false);
        SITHCallback.setSwipeEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Update list
        this.weapons = getListOfWeapons(sceneId, shootId);
        adapter.updateItems(this.weapons);
        adapter.notifyDataSetChanged();
    }

    private static List<Item> getListOfWeapons(int sceneId, int shootId) {
        final List<Item> itemWeapons = new ArrayList<>();
        final List<Weapon> weapons = ApplicationConfig.getDaoFactory().getWeaponDao().getWeapons(shootId);

        itemWeapons.addAll(weapons);

        return itemWeapons;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditWeaponFragment.");
        goToEditWeaponFragment();
    }

    public void goToDetailWeaponFragment(int position) {
        Log.d(TAG, "goToDetailWeaponFragment: Returning");
        Fragment detailWeaponFragment = new DetailWeaponFragment();

        final Weapon chosenWeapon = (Weapon) this.weapons.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, this.sceneId);
        bundle.putInt(ViewSceneActivity.SHOOT_ID_KEY, this.shootId);
        bundle.putInt(ViewSceneActivity.WEAPON_ID_KEY, chosenWeapon.getId());
        detailWeaponFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailWeaponFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToEditWeaponFragment() {
        Log.d(TAG, "goToEditWeaponFragment: Returning");
        Fragment editWeaponFragment = new EditWeaponFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, this.sceneId);
        bundle.putInt(ViewSceneActivity.SHOOT_ID_KEY, this.shootId);
        editWeaponFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editWeaponFragment)
                .addToBackStack(null)
                .commit();
    }
}
