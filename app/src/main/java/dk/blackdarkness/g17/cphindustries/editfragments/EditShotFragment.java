package dk.blackdarkness.g17.cphindustries.editfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.createfragments.CreateShotFragment;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditShotFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "EditShotFragment";

    private TextView dummy;
    private FloatingActionButton lock, add;
    private Fragment createShotFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_shot_layout, container, false);
        dummy = view.findViewById(R.id.editShotDummy);
        add = view.findViewById(R.id.createFab);
        lock = view.findViewById(R.id.lockFab);
        initDisplay();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Shots");
        dummy.setText("<Shots in edittext list view here>");
        lock.setOnClickListener(this);
        add.setOnClickListener(this);
        add.setVisibility(View.VISIBLE);
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
        createShotFragment = new CreateShotFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, createShotFragment)
                .addToBackStack(null)
                .commit();
    }
}
