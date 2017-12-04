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
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import dk.blackdarkness.g17.cphindustries.NavListItem;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.RecyclerListAdapter;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.activities.ShotViewActivity;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.editfragments.EditSceneFragment;

import dk.blackdarkness.g17.cphindustries.helper.OnStartDragListener;
import dk.blackdarkness.g17.cphindustries.helper.SimpleItemTouchHelperCallback;

/**
 * Created by Thoma on 11/02/2017.
 */

public class SceneViewFragment extends Fragment implements View.OnClickListener, OnStartDragListener {

    private View view;
    private static final String TAG = "SceneViewFragment";
    private Fragment editSceneFragment, createSceneFragment;
    private FloatingActionButton lock;
    private Button /*goNext,*/ editScene;

    private RecyclerView recyclerView;
    private ItemTouchHelper mItemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_scene_view_layout, container, false);
        // goNext = view.findViewById(R.id.openScene); // TODO: Disabled because I commented the "next" button out
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

        //erstattes med liste af nuværende scener
//        goNext.setOnClickListener(this);
//        goNext.setText("Scene #1");

        //Fjern back-knap (dette er startsiden)
        ((SceneViewActivity)getActivity()).resetActionBar(false);

        // Initiate list view
        this.recyclerView = this.view.findViewById(R.id.fr_scene_recyclerView);

        List<NavListItem> scenes = new ArrayList<NavListItem>();
        scenes.add(new NavListItem(new Scene(1, "1 - The shooting scene"), false));
        scenes.add(new NavListItem(new Scene(22, "22 - Robbing the Bank"), false));
        scenes.add(new NavListItem(new Scene(53, "54 - The escape"), false));

        RecyclerListAdapter adapter = new RecyclerListAdapter(getActivity(), this, scenes);
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        SimpleItemTouchHelperCallback SITHCallback = new SimpleItemTouchHelperCallback(adapter);
        SITHCallback.setDragEnabled(false);
        SITHCallback.setSwipeEnabled(false);

        mItemTouchHelper = new ItemTouchHelper(SITHCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);


        this.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToShotViewActivity();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fr_scene_recyclerView:
                goToShotViewActivity(); break;
            case R.id.lockFab:
                Log.d(TAG, "onClick: lockFab. Returning EditSceneFragment.");
                goToEditSceneFragment();
                break;
        }
    }

    public void goToEditSceneFragment() {
        editSceneFragment = new EditSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.scene_fragment_container, editSceneFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToShotViewActivity() {
        Intent shotView = new Intent(getActivity(), ShotViewActivity.class);
        Log.d(TAG, "onClick: Testbutton. Attempting to start shotview activity.");
        startActivity(shotView);
        getActivity().finish();
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
