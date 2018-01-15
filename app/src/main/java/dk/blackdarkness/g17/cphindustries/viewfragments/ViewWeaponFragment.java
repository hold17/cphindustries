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
import android.widget.Toast;

import java.util.List;

import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponFragment;
import dk.blackdarkness.g17.cphindustries.entityfragments.DetailWeaponFragment;

import dk.blackdarkness.g17.cphindustries.helper.BreadcrumbHelper;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;

public class ViewWeaponFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "ViewWeaponFragment";
    private FloatingActionButton lock;
    private int sceneId;
    private int shootId;
    private RecyclerView recyclerView;
    private StdRecListAdapter adapter;
    private WeaponDao weaponDao;
    private ShootDao shootDao;
    private SceneDao sceneDao;
    private List<Item> weapons;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weapon_view_layout, container, false);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.recyclerView = this.view.findViewById(R.id.fr_weapon_recyclerView);

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
        ((ViewSceneActivity)getActivity()).setActionBarSubtitle(BreadcrumbHelper.getSubtitle(sceneDao.getScene(sceneId), shootDao.getShoot(shootId)));
        this.lock.setOnClickListener(this);

        this.weapons = ItemConverter.weaponToItem(this.weaponDao.getListByShoot(shootId));

        final RecyclerViewClickListener listener = (v, position) -> goToDetailWeaponFragment(position);

        this.adapter = new StdRecListAdapter(getActivity(), this.weapons, listener);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.CACHE_CLEARED)) {
            Toast.makeText(getContext(), "Cache has been cleared", Toast.LENGTH_SHORT).show();
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.CACHE_CLEARED);
            this.weapons = ItemConverter.weaponToItem(this.weaponDao.getListByShoot(shootId));
            adapter.updateItems(this.weapons);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditWeaponFragment.");
        goToEditWeaponFragment();
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

    public void goToDetailWeaponFragment(int position) {
        Log.d(TAG, "goToDetailWeaponFragment: Returning");
        Fragment detailWeaponFragment = new DetailWeaponFragment();

        final int chosenWeapon = this.weapons.get(position).getId();
        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, this.sceneId);
        bundle.putInt(ViewSceneActivity.SHOOT_ID_KEY, this.shootId);
        bundle.putInt(ViewSceneActivity.WEAPON_ID_KEY, chosenWeapon);
        detailWeaponFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailWeaponFragment)
                .addToBackStack(null)
                .commit();
    }
}
