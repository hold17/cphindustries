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

import dk.blackdarkness.g17.cphindustries.dto.ConnectionStatus;
import dk.blackdarkness.g17.cphindustries.dto.FireMode;
import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.SimpleListAdapter;
import dk.blackdarkness.g17.cphindustries.dto.Weapon;
import dk.blackdarkness.g17.cphindustries.editfragments.EditWeaponFragment;
import dk.blackdarkness.g17.cphindustries.entityfragments.WeaponFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class WeaponViewFragment extends Fragment implements View.OnClickListener {

    private View view;
    private static final String TAG = "WeaponViewFragment";
//    private Button goNext;
    private FloatingActionButton lock;
    private Fragment weaponFragment, editWeaponFragment;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_weapon_view_layout, container, false);
//        goNext = view.findViewById(R.id.openWeapon);
        lock = view.findViewById(R.id.lockFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Shots");
//        goNext.setOnClickListener(this);
//        goNext.setText("Weapon #1");
        lock.setOnClickListener(this);

        this.listView = (ListView) this.view.findViewById(R.id.fr_weapon_listView);
//        NavListItem[] listItems = {
//                new NavListItem(true, "Weapon 1", FireMode.BURST, ConnectionStatus.NO_CONNECTION),
//                new NavListItem(false, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.BAR_0),
//                new NavListItem(false, "Weapon 3", FireMode.SINGLE, ConnectionStatus.BAR_3),
//                new NavListItem(false, "Weapon 4", FireMode.SAFE, ConnectionStatus.FULL),
//                new NavListItem(true, "Weapon 5", FireMode.BURST, ConnectionStatus.BAR_1)
//        };
        Weapon[] weapons = {
                new Weapon(0, "Weapon 1", FireMode.BURST, ConnectionStatus.NO_CONNECTION),
                new Weapon(1, "Weapon 2", FireMode.FULL_AUTO, ConnectionStatus.BAR_0),
                new Weapon(2, "Weapon 3", FireMode.SINGLE, ConnectionStatus.BAR_3),
                new Weapon(3, "Weapon 4", ConnectionStatus.FULL), // Default to SAFE mode
                new Weapon(4, "Weapon 5", FireMode.BURST, ConnectionStatus.BAR_1)
        };
        ListAdapter adapter = new SimpleListAdapter(getActivity(), weapons);
        this.listView.setAdapter(adapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToWeaponFragment();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
//            case R.id.openWeapon:
//                goToWeaponFragment();
//                break;
            case R.id.lockFab:
                Log.d(TAG, "onClick: lockFab. Returning EditWeaponFragment.");
                goToEditWeaponFragment();
                break;
        }
    }

    public void goToWeaponFragment() {
        Log.d(TAG, "onClick: Trying to open weapon fragment.");
        weaponFragment = new WeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, weaponFragment).addToBackStack(null).commit();
    }

    public void goToEditWeaponFragment() {
        editWeaponFragment = new EditWeaponFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editWeaponFragment)
                .addToBackStack(null)
                .commit();
    }

}
