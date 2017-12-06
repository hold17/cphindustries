package dk.blackdarkness.g17.cphindustries.viewfragments;

import android.content.Intent;
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

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.activities.ShotViewActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.editfragments.EditSceneFragment;

import dk.blackdarkness.g17.cphindustries.recyclerview.NavListItem;
import dk.blackdarkness.g17.cphindustries.recyclerview.RecyclerListAdapter;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_scene_view_layout, container, false);
        lock = view.findViewById(R.id.lockFab);
        Log.d(TAG, "onCreateView: Returning.");
        return view;
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Scenes");
        lock.setOnClickListener(this);

        //Fjern back-knap (dette er startsiden)
        ((SceneViewActivity)getActivity()).resetActionBar(false);

        RecyclerView recyclerView = this.view.findViewById(R.id.fr_scene_recyclerView);

//        List<NavListItem> scenes = new ArrayList<>();
//        scenes.add(new NavListItem(new Scene(1, "1 - The shooting scene"), false));
//        scenes.add(new NavListItem(new Scene(22, "22 - Robbing the Bank"), false));
//        scenes.add(new NavListItem(new Scene(53, "54 - The escape"), false));

        final RecyclerViewClickListener listener = (v, position) -> goToShotViewActivity(position);

//        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, scenes, listener);
        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, getListOfNavListItemsWithScenes(), listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(false);
        SITHCallback.setSwipeEnabled(false);

        mItemTouchHelper = new ItemTouchHelper(SITHCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private static List<NavListItem> getListOfNavListItemsWithScenes() {
        final List<NavListItem> navListScenes = new ArrayList<>();
        final List<Scene> scenes = ApplicationConfig.getDaoFactory().getSceneDao().get();

        for (Scene s : scenes) {
            navListScenes.add(new NavListItem(s, false));
        }

        return navListScenes;
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditSceneFragment.");
        goToEditSceneFragment();
    }

    public void goToEditSceneFragment() {
        Log.d(TAG, "goToEditSceneFragment: Returning");
        Fragment editSceneFragment = new EditSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.scene_fragment_container, editSceneFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToShotViewActivity(int position) {
        Log.d(TAG, "goToShotViewActivity: Returning");
        Intent shotView = new Intent(getActivity(), ShotViewActivity.class);
        shotView.putExtra("SCENE_ID", ApplicationConfig.getDaoFactory().getSceneDao().get().get(position).getId());

//        Toast.makeText(getContext().getApplicationContext(), "Index: " + position + ", id: " + ApplicationConfig.getDaoFactory().getSceneDao().get().get(position).getId(), Toast.LENGTH_LONG).show();
        startActivity(shotView);
        getActivity().finish();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
