package dk.blackdarkness.g17.cphindustries.createfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.MainActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.helper.SoftInputHelper;

public class CreateShotFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "CreateShotFragment";
    private TextView submitSave, submitCancel;
    private EditText shootNameText;
    private int sceneId;
    private ShootDao shootDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_shot_layout, container, false);
        Log.d(TAG, "onCreateView: Returning.");

        shootNameText = view.findViewById(R.id.fr_create_shot_editText);
        submitSave = view.findViewById(R.id.fr_create_shot_tvSave);
        submitCancel = view.findViewById(R.id.fr_create_shot_tvCancel);

        this.sceneId = getArguments().getInt(MainActivity.SCENE_ID_KEY);

        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Create Shoot");

        // handle softInput and focus
        shootNameText.setFocusableInTouchMode(true);
        shootNameText.requestFocus();
        SoftInputHelper.showSoftInput(getContext(), shootNameText);
        // click on view dismisses softInput
        view.setOnClickListener(this);

        submitSave.setOnClickListener(this);
        submitCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fr_create_shot_tvSave:
                saveClicked();
                getActivity().onBackPressed();
                break;
            case R.id.fr_create_shot_tvCancel:
                getActivity().onBackPressed();
                break;
            // handle click on view (layout)
            case R.id.fr_create_shot_layout:
                SoftInputHelper.hideSoftInput(getContext(), view);
                break;
        }
    }

    private void saveClicked() {
        SoftInputHelper.hideSoftInput(getContext(), view);
        final Shoot newShoot = new Shoot(-1, this.shootNameText.getText().toString(), this.sceneId);
        Log.d(TAG, "saveClicked: creating shoot: " + newShoot.toString());
        this.shootDao.create(newShoot);
    }

}
