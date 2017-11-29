package dk.blackdarkness.g17.cphindustries.viewfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.SimpleListAdapter;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.editfragments.EditShotFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class ShotViewFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "ShotViewFragment";
    private Fragment weaponViewFragment, editShotFragment;
    private FloatingActionButton lock;
//    private Button goNext;

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_shot_view_layout, container, false);
//        goNext = view.findViewById(R.id.openShot);
        lock = view.findViewById(R.id.lockFab);
        initLayout();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initLayout() {
        getActivity().setTitle("Scenes");
        lock.setOnClickListener(this);
//        goNext.setOnClickListener(this);
//        goNext.setText("Shot #1");

        this.listView = (ListView) this.view.findViewById(R.id.fr_shot_listView);
//        NavListItem[] listItems = {
//            new NavListItem(true, "Shoot 1"),
//            new NavListItem(false, "Shoot 2"),
//            new NavListItem(false, "Shoot 3")
//        };
        Shoot[] shoots = {
                new Shoot(0, "Shoot 1"),
                new Shoot(1, "Shoot 2"),
                new Shoot(2, "Shoot 3")
        };

        ListAdapter adapter = new SimpleListAdapter(getActivity(), shoots);
        this.listView.setAdapter(adapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToWeaponViewFragment();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.openShot:
//                goToWeaponViewFragment();
//                break;
            case R.id.fr_shot_listView:
                    goToWeaponViewFragment(); break;
            case R.id.lockFab:
                Log.d(TAG, "onClick: lockFab. Returning EditShotFragment.");
                goToEditShotFragment();
                break;
        }
    }

    public void goToEditShotFragment() {
        Log.d(TAG, "goToEditShotFragment: Trying to open.");
        editShotFragment = new EditShotFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editShotFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToWeaponViewFragment() {
        Log.d(TAG, "onClick: Trying to open.");
        weaponViewFragment = new WeaponViewFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weaponViewFragment).addToBackStack(null).commit();
    }

}
