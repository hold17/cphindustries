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
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.editfragments.EditSceneFragment;

import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.SimpleItemTouchHelperCallback;

public class ViewSceneFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "ViewSceneFragment";
    private FloatingActionButton lock;
    private SceneDao sceneDao;
    private StdRecListAdapter adapter;

    private List<Item> scenes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_scene_view_layout, container, false);
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        SharedPreferenceManager.init(getContext());

        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity)getActivity()).setActionBarTitle("Scenes");
        ((ViewSceneActivity)getActivity()).setActionBarSubtitle("");
        lock.setOnClickListener(this);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_scene_recyclerView);

        final RecyclerViewClickListener listener = (v, position) -> goToViewShotFragment(position);

        this.scenes = getListOfScenes();

        adapter = new StdRecListAdapter(getActivity(), this.scenes, listener);
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
        adapter.notifyDataSetChanged();
    }

    public static List<Item> getListOfScenes() {
        final List<Item> itemScenes = new ArrayList<>();
        final List<Scene> scenes =  ApplicationConfig.getDaoFactory().getSceneDao().get();

        itemScenes.addAll(scenes);

        return itemScenes;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditSceneFragment.");
        goToEditSceneFragment();
    }

    public void goToEditSceneFragment() {
        Log.d(TAG, "goToEditSceneFragment: Returning");
        ((ViewSceneActivity)getActivity()).enableActionBar(true);
        Fragment editSceneFragment = new EditSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editSceneFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToViewShotFragment(int position) {
        Scene chosenScene = (Scene) this.scenes.get(position);

        Log.d(TAG, "goToShotViewFragment: Returning");
        ((ViewSceneActivity)getActivity()).enableActionBar(true);
        Fragment shotViewFragment = new ViewShotFragment();

        //final Scene chosenScene = this.sceneDao.get().get(this.scenes.get(position).getId());
        System.out.println("SceneID: " + chosenScene.getId());
        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, chosenScene.getId());
        shotViewFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, shotViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
