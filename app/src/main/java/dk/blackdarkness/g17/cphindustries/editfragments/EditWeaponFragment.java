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
import dk.blackdarkness.g17.cphindustries.createfragments.CreateWeaponFragment;
import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditWeaponFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EditWeaponFragment";

    private FloatingActionButton lock, add;
    private Fragment createWeaponFragment;
    private ListView listView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_edit_weapon_layout, container, false);
        this.lock = view.findViewById(R.id.lockFab);
        this.add = view.findViewById(R.id.createFab);
        initDisplay();
        Log.d(TAG, "onCreateVisew: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Edit Weapon");
        this.lock.setOnClickListener(this);
        this.add.setOnClickListener(this);
        this.add.setVisibility(View.VISIBLE);

        // Initiate list view
        this.listView = this.view.findViewById(R.id.fr_editWeapon_listView);

        NavListItem[] weapons  = {
                new NavListItem(new Weapon(0, "Weapon 1", FireMode.BURST, ConnectionStatus.NO_CONNECTION), true),
                new NavListItem(new Weapon(1, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.BAR_0), true),
                new NavListItem(new Weapon(2, "Weapon 3", FireMode.SINGLE, ConnectionStatus.BAR_3), true),
                new NavListItem(new Weapon(3, "Weapon 4", ConnectionStatus.FULL), true), // Default to SAFE mode
                new NavListItem(new Weapon(4, "Weapon 5", FireMode.BURST, ConnectionStatus.BAR_1), true)
        };

        ListAdapter adapter = new SimpleListAdapter(getActivity(), weapons);
        this.listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lockFab:
                checkLock();
                break;
            case R.id.createFab:
                goToCreateWeaponFragment();
                break;
        }
    }

    public void checkLock() {
            //Skal erstattes med save data
            Log.d(TAG, "checkLock: Lock pressed. Should save input data.");
            getActivity().onBackPressed();
    }

    public void goToCreateWeaponFragment() {
        Log.d(TAG, "goToCreateWeaponFragment: Returning.");
        this.createWeaponFragment = new CreateWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createWeaponFragment)
                .addToBackStack(null)
                .commit();
    }
}
