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
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.editfragments.EditSceneFragment;

import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

/**
 * Created by Thoma on 11/02/2017.
 */

public class SceneViewFragment extends Fragment implements View.OnClickListener, OnStartDragListener {
    private View view;
    private static final String TAG = "SceneViewFragment";
    private FloatingActionButton lock;
    private ItemTouchHelper mItemTouchHelper;
    private SceneDao sceneDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_scene_view_layout, container, false);
        SharedPreferenceManager.init(getContext());
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferenceManager.init(getContext());

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_scene_recyclerView);

        final RecyclerViewClickListener listener = (v, position) -> goToShotViewFragment(position);

//        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, scenes, listener);
        StdRecListAdapter adapter = new StdRecListAdapter(getActivity(), this, getListOfScenes(), listener);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SceneViewActivity)getActivity()).setActionBarTitle("Scenes");
        ((SceneViewActivity)getActivity()).setActionBarSubtitle("");

        lock.setOnClickListener(this);
    }

    private static List<Item> getListOfScenes() {
      final List<Item> itemScenes = new ArrayList<>();
        final List<Scene> scenes =  ApplicationConfig.getDaoFactory().getSceneDao().get();

        for (Scene s : scenes) {
            itemScenes.add(s);
        }

        return itemScenes;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditSceneFragment.");
        goToEditSceneFragment();
    }

    public void goToEditSceneFragment() {
        Log.d(TAG, "goToEditSceneFragment: Returning");
        ((SceneViewActivity)getActivity()).enableActionBar(true);
        Fragment editSceneFragment = new EditSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editSceneFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToShotViewFragment(int position) {
        Log.d(TAG, "goToShotViewFragment: Returning");
        ((SceneViewActivity)getActivity()).enableActionBar(true);
        Fragment shotViewFragment = new ShotViewFragment();

        final Scene chosenScene = this.sceneDao.get().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(SceneViewActivity.SCENE_ID_KEY, chosenScene.getId());
        shotViewFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, shotViewFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
