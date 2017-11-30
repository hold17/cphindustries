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
import dk.blackdarkness.g17.cphindustries.createfragments.CreateShotFragment;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditShotFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EditShotFragment";

    private View view;
    private FloatingActionButton lock, add;
    private Fragment createShotFragment;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_shot_layout, container, false);
        this.add = view.findViewById(R.id.createFab);
        this.lock = view.findViewById(R.id.lockFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Edit Shot");
        this.lock.setOnClickListener(this);
        this.add.setOnClickListener(this);
        this.add.setVisibility(View.VISIBLE);

        // Initiate list view
        this.listView = this.view.findViewById(R.id.fr_editShot_listView);
//        String[] foods = { "1 - The shooting scene", "22 - Robbing the Bank", "54 - The escape" };
        NavListItem[] shoots = {
                new NavListItem(new Shoot(0, "Shoot 1"), true),
                new NavListItem(new Shoot(1, "Shoot 2"), true),
                new NavListItem(new Shoot(2, "Shoot 3"), true)
        };

        // ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, foods);
        ListAdapter adapter = new SimpleListAdapter(getActivity(), shoots);
        this.listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                checkLock();
                break;
            case R.id.createFab:
                goToCreateShot();
                break;
        }
    }

    public void checkLock() {
            Log.d(TAG, "checkLock: Should save input data.");
            getActivity().onBackPressed();
    }
    public void goToCreateShot() {
        this.createShotFragment = new CreateShotFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createShotFragment)
                .addToBackStack(null)
                .commit();
    }
}
