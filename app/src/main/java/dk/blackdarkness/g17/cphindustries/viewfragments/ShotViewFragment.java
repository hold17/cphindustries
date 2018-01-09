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
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.editfragments.EditShotFragment;

import dk.blackdarkness.g17.cphindustries.helper.BreadcrumbHelper;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

/**
 * Created by Thoma on 11/02/2017.
 */

public class ShotViewFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "ShotViewFragment";
    private FloatingActionButton lock;
    private int sceneId = -1;
    private ShootDao shootDao;
    private SceneDao sceneDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_shot_view_layout, container, false);
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneId = getArguments().getInt(SceneViewActivity.SCENE_ID_KEY);
        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();
        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SceneViewActivity)getActivity()).setActionBarTitle("Shoots");
        ((SceneViewActivity)getActivity()).setActionBarSubtitle(BreadcrumbHelper.getSubtitle(sceneDao.get(sceneId)));
        lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_shot_recyclerView);

        final RecyclerViewClickListener listener = (v, position) -> goToWeaponViewFragment(position);

        final List<Item> shoots = ItemConverter.shootToItem(ApplicationConfig.getDaoFactory().getShootDao().get(sceneId));

        StdRecListAdapter adapter = new StdRecListAdapter(getActivity(), shoots, listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(false);
        SITHCallback.setSwipeEnabled(false);

    }

//    private static List<Item> getListOfShoots(int sceneId) {
//        final List<Item> itemShoots = new ArrayList<>();
//        final List<Shoot> shoots = ApplicationConfig.getDaoFactory().getShootDao().get(sceneId);
//
//        for (Shoot s : shoots) {
//            itemShoots.add(s);
//        }
//
//        return itemShoots;
//    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditShotFragment.");
        goToEditShotFragment();
    }

    public void goToEditShotFragment() {
        Log.d(TAG, "goToEditShotFragment: Returning");
        Fragment editShotFragment = new EditShotFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("sceneId", this.sceneId);
        editShotFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editShotFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToWeaponViewFragment(int position) {
        Log.d(TAG, "goToWeaponViewFragment: Returning");
        Fragment weaponViewFragment = new WeaponViewFragment();

//        Toast.makeText(getContext().getApplicationContext(), "Index: " + position + ", ID = " + ApplicationConfig.getDaoFactory().getShootDao().get(this.sceneId).get(position).getId(), Toast.LENGTH_LONG).show();

        // Add shoot ID to arguemnts
        final Shoot chosenShoot = this.shootDao.get(this.sceneId).get(position);

        Bundle bundle = new Bundle();
        bundle.putInt(SceneViewActivity.SCENE_ID_KEY, this.sceneId);
        bundle.putInt(SceneViewActivity.SHOOT_ID_KEY, chosenShoot.getId());
        weaponViewFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weaponViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
