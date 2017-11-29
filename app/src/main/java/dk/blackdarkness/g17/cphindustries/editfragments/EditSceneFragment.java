package dk.blackdarkness.g17.cphindustries.editfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.NavListItem;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.SimpleListAdapter;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateSceneFragment;
import dk.blackdarkness.g17.cphindustries.dto.Scene;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditSceneFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EditSceneFragment";

    private View view;
    private FloatingActionButton add, lock;
    private Fragment createSceneFragment;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_scene_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Edit Scene");
        ((SceneViewActivity)getActivity()).resetActionBar(true);
        this.add.setVisibility(View.VISIBLE);
        this.add.setOnClickListener(this);
        this.lock.setOnClickListener(this);

        // Initiate list view
        this.listView = this.view.findViewById(R.id.fr_editScene_listView);
//        String[] foods = { "1 - The shooting scene", "22 - Robbing the Bank", "54 - The escape" };
        NavListItem[] scenes = {
                new NavListItem(new Scene(1, "1 - The shooting scene"), true),
                new NavListItem(new Scene(22, "22 - Robbing the Bank"), true),
                new NavListItem(new Scene(53, "54 - The escape"), true),
        };

        // ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, foods);
        ListAdapter adapter = new SimpleListAdapter(getActivity(), scenes);
        this.listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                checkLock();
                break;
            case R.id.createFab:
                goToCreateSceneFragment();
                break;
        }
    }

    public void checkLock() {
        Log.d(TAG, "checkLock: Should save input data.");
        getActivity().onBackPressed();
    }

    public void goToCreateSceneFragment() {
        createSceneFragment = new CreateSceneFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.scene_fragment_container, createSceneFragment)
                .addToBackStack(null)
                .commit();
    }

}
