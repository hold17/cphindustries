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

import java.util.List;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.MainActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.SharedPreferenceManager;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.WeaponDao;
import dk.blackdarkness.g17.cphindustries.dto.Item;
import dk.blackdarkness.g17.cphindustries.editfragments.EditFragment;
import dk.blackdarkness.g17.cphindustries.helper.FragmentType;
import dk.blackdarkness.g17.cphindustries.helper.ItemConverter;
import dk.blackdarkness.g17.cphindustries.menuitems.SettingsFragment;
import dk.blackdarkness.g17.cphindustries.recyclerview.StdRecListAdapter;
import dk.blackdarkness.g17.cphindustries.recyclerview.helpers.RecyclerViewClickListener;

public class ViewFragment extends Fragment implements View.OnClickListener {
    private FragmentType FRAGMENT_TYPE;
    private static final String TAG = "ViewFragment";
    private View view;
    private FloatingActionButton lock;
    private RecyclerView recyclerView;
    private StdRecListAdapter adapter;
    private SceneDao sceneDao;
    private ShootDao shootDao;
    private WeaponDao weaponDao;
    private List<Item> items;
    private int sceneId;
    private int shootId;
    private int weaponId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view, container, false);
        lock = view.findViewById(R.id.lockFab);
        FRAGMENT_TYPE = (FragmentType) getArguments().getSerializable(MainActivity.FRAGMENT_TYPE_KEY);

        if (savedInstanceState != null)
            Log.d(TAG, "onCreateView: savedInstanceState contents: " + savedInstanceState.toString());
        Log.d(TAG, "onCreateView: bundle contents: " + getArguments().toString());

        Log.d(TAG, FRAGMENT_TYPE + " onCreateView: Returning.");

        recyclerView = view.findViewById(R.id.fr_view_recyclerView);

        SharedPreferenceManager.init(getContext());

        // only set the ones needed throws AssertException if FRAGMENT_TYPE is null
        assert FRAGMENT_TYPE != null;
        switch (FRAGMENT_TYPE) {
            case SHOOTS:
                sceneId = getArguments().getInt(MainActivity.SCENE_ID_KEY);
                shootDao = ApplicationConfig.getDaoFactory().getShootDao();
                break;
            case WEAPONS:
                shootId = getArguments().getInt(MainActivity.SHOOT_ID_KEY);
                weaponDao = ApplicationConfig.getDaoFactory().getWeaponDao();
                break;
            default:
                sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();
                break;
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle(FRAGMENT_TYPE.getTypeAsString(getContext(), 2));
        // SCENES is the top level, so no subtitle
        if (FRAGMENT_TYPE != FragmentType.SCENES)
            ((MainActivity)getActivity()).setActionBarSubtitle(getArguments().getString(MainActivity.SUBTITLE_KEY));
        else ((MainActivity)getActivity()).setActionBarSubtitle("");
        lock.setOnClickListener(this);

        // ideally there should not be an item converter for each item type and instead the dao should just always return items
        switch (FRAGMENT_TYPE) {
            case SCENES: items = ItemConverter.sceneToItem(sceneDao.getList()); break;
            case SHOOTS: items = ItemConverter.shootToItem(shootDao.getListByScene(sceneId)); break;
            case WEAPONS: items = ItemConverter.weaponToItem(weaponDao.getListByShoot(shootId)); break;
        }

        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int itemId) {
                switch (FRAGMENT_TYPE) {
                    case SCENES: sceneId = itemId; break;
                    case SHOOTS: shootId = itemId; break;
                    case WEAPONS: weaponId = itemId; break;
                }
                goToViewFragment();
            }

            @Override
            public boolean onLongClick(View view, int itemId) {
                goToEditFragment();
                return false;
            }
        };

        adapter = new StdRecListAdapter(getActivity(), items, listener);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean cacheIsCleared = SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.CACHE_CLEARED);
        boolean appIsReset = SharedPreferenceManager.getInstance().getBoolean(SettingsFragment.APP_RESET);

        if(cacheIsCleared || appIsReset) {
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.CACHE_CLEARED);
            SharedPreferenceManager.getInstance().saveBoolean(false, SettingsFragment.APP_RESET);
            // ideally there should not be an item converter for each item type and instead the dao should just always return items
            switch (FRAGMENT_TYPE) {
                case SCENES: items = ItemConverter.sceneToItem(sceneDao.getList()); break;
                case SHOOTS: items = ItemConverter.shootToItem(shootDao.getListByScene(sceneId)); break;
                case WEAPONS: items = ItemConverter.weaponToItem(weaponDao.getListByShoot(shootId)); break;
            }
            adapter.updateItems(items);
        }
        Log.d(TAG, "Items onResume: " + adapter.getItemCount());
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: lockFab. Returning EditFragment.");
        goToEditFragment();
    }

    public void goToEditFragment() {
        Log.d(TAG, "goToEditFragment: Returning");
        // awaiting consolidated editFragment
        EditFragment editFragment = new EditFragment();

        // Make bundle for edit fragment
        Bundle bundle = new Bundle();
        if (FRAGMENT_TYPE != FragmentType.SCENES) {
            if (FRAGMENT_TYPE == FragmentType.SHOOTS) {
                bundle.putInt(MainActivity.SCENE_ID_KEY, sceneId);
                bundle.putSerializable(MainActivity.FRAGMENT_TYPE_KEY, FragmentType.SHOOTS);
            }
            else if (FRAGMENT_TYPE == FragmentType.WEAPONS) {
                bundle.putInt(MainActivity.SHOOT_ID_KEY, shootId);
                bundle.putSerializable(MainActivity.FRAGMENT_TYPE_KEY, FragmentType.WEAPONS);
            }
            bundle.putString(MainActivity.SUBTITLE_KEY, getArguments().getString(MainActivity.SUBTITLE_KEY));
        } else {
            bundle.putSerializable(MainActivity.FRAGMENT_TYPE_KEY, FragmentType.SCENES);
        }
        editFragment.setArguments(bundle);
        Log.d(TAG, "goToEditFragment: bundle contents: " + bundle.toString());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, editFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToViewFragment() {
        Log.d(TAG, "goToViewFragment: Returning");
        ((MainActivity)getActivity()).enableActionBar(true);
        Fragment viewFragment;

        // Make subtitle and bundle for next view
        Bundle bundle = new Bundle();
        if (FRAGMENT_TYPE != FragmentType.WEAPONS) {
            StringBuilder sb = new StringBuilder();
            if (getArguments().getString(MainActivity.SUBTITLE_KEY) != null)
                sb.append(getArguments().getString(MainActivity.SUBTITLE_KEY));
            if (FRAGMENT_TYPE == FragmentType.SCENES) {
                sb.append(sceneDao.getScene(sceneId).getName());
                bundle.putInt(MainActivity.SCENE_ID_KEY, sceneId);
                bundle.putSerializable(MainActivity.FRAGMENT_TYPE_KEY, FragmentType.SHOOTS);
            }
            else if (FRAGMENT_TYPE == FragmentType.SHOOTS) {
                sb.append(", ").append(shootDao.getShoot(shootId).getName());
                bundle.putInt(MainActivity.SHOOT_ID_KEY, shootId);
                bundle.putSerializable(MainActivity.FRAGMENT_TYPE_KEY, FragmentType.WEAPONS);
            }
            bundle.putString(MainActivity.SUBTITLE_KEY, sb.toString());
            viewFragment = new ViewFragment();
        } else {
            bundle.putInt(MainActivity.WEAPON_ID_KEY, weaponId);
            viewFragment = new ViewWeaponDetailsFragment();
        }
        viewFragment.setArguments(bundle);
        Log.d(TAG, "goToViewFragment: bundle contents: " + bundle.toString());

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, viewFragment)
                .addToBackStack(null)
                .commit();
    }
}
