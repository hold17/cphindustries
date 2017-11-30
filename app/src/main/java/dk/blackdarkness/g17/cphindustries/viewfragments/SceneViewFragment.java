package dk.blackdarkness.g17.cphindustries.viewfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import dk.blackdarkness.g17.cphindustries.NavListItem;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.SimpleListAdapter;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;
import dk.blackdarkness.g17.cphindustries.activities.ShotViewActivity;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.editfragments.EditSceneFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class SceneViewFragment extends Fragment implements View.OnClickListener {

    private View view;
    private static final String TAG = "SceneViewFragment";
    private Fragment editSceneFragment, createSceneFragment;
    private FloatingActionButton lock;
    private Button /*goNext,*/ editScene;

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_scene_view_layout, container, false);
        // goNext = view.findViewById(R.id.openScene); // TODO: Disabled because I commented the "next" button out
        lock = view.findViewById(R.id.lockFab);
        initLayout();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }


    public void initLayout() {
        getActivity().setTitle("Scenes");
        lock.setOnClickListener(this);

        //erstattes med liste af nuværende scener
//        goNext.setOnClickListener(this);
//        goNext.setText("Scene #1");

        //Fjern back-knap (dette er startsiden)
        ((SceneViewActivity)getActivity()).resetActionBar(false);

        // Initiate list view
        this.listView = (ListView) this.view.findViewById(R.id.fr_scene_listView);
//        String[] foods = { "1 - The shooting scene", "22 - Robbing the Bank", "54 - The escape" };
//        NavListItem[] scenes = {
//                new NavListItem(false, "1 - The shooting scene"),
//                new NavListItem(false, "22 - Robbing the Bank"),
//                new NavListItem(false, "54 - The escape")
//        };
        NavListItem[] scenes = {
                new NavListItem(new Scene(1, "1 - The shooting scene"), false),
                new NavListItem(new Scene(22, "22 - Robbing the Bank"), false),
                new NavListItem(new Scene(53, "54 - The escape"), false),
        };
//        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, foods);
        ListAdapter adapter = new SimpleListAdapter(getActivity(), scenes);
        this.listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToShotViewActivity();
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            /*case R.id.openScene:
                goToShotViewActivity();
                break;*/
            case R.id.fr_scene_listView:
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

}
