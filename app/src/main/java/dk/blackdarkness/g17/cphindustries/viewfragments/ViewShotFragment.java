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

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewMainActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.editfragments.EditShotFragment;
import dk.blackdarkness.g17.cphindustries.helper.BreadcrumbHelper;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;
import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

public class ViewShotFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "ViewShotFragment";
    private FloatingActionButton lock;
    private int sceneId;
    private RecyclerView recyclerView;
    private StdRecListAdapter adapter;
    private ShootDao shootDao;
    private SceneDao sceneDao;
    private List<Item> shoots;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_shot_view_layout, container, false);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.recyclerView = this.view.findViewById(R.id.fr_shot_recyclerView);

        SharedPreferenceManager.init(getContext());

        this.sceneId = getArguments().getInt(ViewMainActivity.SCENE_ID_KEY);

        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();
        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewMainActivity)getActivity()).setActionBarTitle("Shoots");
        ((ViewMainActivity)getActivity()).setActionBarSubtitle(BreadcrumbHelper.getSubtitle(sceneDao.getScene(sceneId)));
        this.lock.setOnClickListener(this);

        this.shoots = ItemConverter.shootToItem(this.shootDao.getListByScene(sceneId));

        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int itemId) {
                goToViewWeaponFragment(itemId);
            }

            @Override
            public boolean onLongClick(View view, int itemId) {
                goToEditShotFragment();
                return false;
            }
        };

        this.adapter = new StdRecListAdapter(getActivity(), shoots, listener);

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
            this.shoots = ItemConverter.shootToItem(this.shootDao.getListByScene(sceneId));
            adapter.updateItems(this.shoots);
        }
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditShotFragment.");
        goToEditShotFragment();
    }

    public void goToEditShotFragment() {
        Log.d(TAG, "goToEditShotFragment: Returning");
        Fragment editShotFragment = new EditShotFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewMainActivity.SCENE_ID_KEY, this.sceneId);
        editShotFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editShotFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToViewWeaponFragment(int shootId) {
        Log.d(TAG, "goToWeaponViewFragment: Returning");
        Fragment weaponViewFragment = new ViewWeaponFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ViewMainActivity.SCENE_ID_KEY, this.sceneId);
        bundle.putInt(ViewMainActivity.SHOOT_ID_KEY, shootId);
        weaponViewFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weaponViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
