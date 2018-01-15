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
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.editfragments.EditSceneFragment;

import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;

public class ViewSceneFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "ViewSceneFragment";
    private FloatingActionButton lock;
    private RecyclerView recyclerView;
    private StdRecListAdapter adapter;
    private SceneDao sceneDao;
    private List<Item> scenes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_scene_view_layout, container, false);
        this.view.setTag(TAG);
        this.lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");

        this.recyclerView = this.view.findViewById(R.id.fr_scene_recyclerView);

        SharedPreferenceManager.init(getContext());

        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewSceneActivity)getActivity()).setActionBarTitle("Scenes");
        ((ViewSceneActivity)getActivity()).setActionBarSubtitle("");
        this.lock.setOnClickListener(this);

        final RecyclerViewClickListener listener = (v, position) -> goToViewShotFragment(position);

        this.scenes = ItemConverter.sceneToItem(this.sceneDao.getList());

        this.adapter = new StdRecListAdapter(getActivity(), this.scenes, listener);

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
            this.scenes = ItemConverter.sceneToItem(this.sceneDao.getList());
            adapter.updateItems(this.scenes);
        }
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
        Log.d(TAG, "goToShotViewFragment: Returning");
        ((ViewSceneActivity)getActivity()).enableActionBar(true);
        Fragment shotViewFragment = new ViewShotFragment();

        final int chosenScene = this.scenes.get(position).getId();
        Bundle bundle = new Bundle();
        bundle.putInt(ViewSceneActivity.SCENE_ID_KEY, chosenScene);
        shotViewFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, shotViewFragment)
                .addToBackStack(null)
                .commit();
    }
}
