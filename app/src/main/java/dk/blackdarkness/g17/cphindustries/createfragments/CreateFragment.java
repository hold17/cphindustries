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
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.helper.FragmentType;
import dk.blackdarkness.g17.cphindustries.helper.SoftInputHelper;

public class CreateFragment  extends Fragment implements View.OnClickListener {
    private FragmentType FRAGMENT_TYPE;
    private static final String TAG = "CreateFragment";
    private View view;
    private TextView titleText, submitSave, submitCancel;
    private EditText nameText;
    private int sceneId;
    private SceneDao sceneDao;
    private ShootDao shootDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create, container, false);
        FRAGMENT_TYPE = (FragmentType) getArguments().getSerializable(MainActivity.FRAGMENT_TYPE_KEY);

        if (savedInstanceState != null)
            Log.d(TAG, "onCreateView: savedInstanceState contents: " + savedInstanceState.toString());
        Log.d(TAG, "onCreateView: bundle contents: " + getArguments().toString());

        Log.d(TAG, FRAGMENT_TYPE + " onCreateView: Returning.");

        nameText = view.findViewById(R.id.fr_create_editText);
        titleText = view.findViewById(R.id.fr_create_title);
        submitSave = view.findViewById(R.id.fr_create_tvSave);
        submitCancel = view.findViewById(R.id.fr_create_tvCancel);

        if (FRAGMENT_TYPE == FragmentType.SCENES) {
            sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();
        }
        else {
            sceneId = getArguments().getInt(MainActivity.SCENE_ID_KEY);
            shootDao = ApplicationConfig.getDaoFactory().getShootDao();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).setActionBarTitle("Create " + FRAGMENT_TYPE.getTypeAsString(getContext(), 1));

        titleText.setText(getString(R.string.create_new, FRAGMENT_TYPE.getTypeAsString(getContext(), 1)));

        view.setOnClickListener(this);

        nameText.setFocusableInTouchMode(true);
        nameText.requestFocus();
        SoftInputHelper.showSoftInput(getContext(), nameText);

        submitSave.setOnClickListener(this);
        submitCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fr_create_tvSave:
                saveClicked();
                SoftInputHelper.hideSoftInput(getContext(), view);
                getActivity().onBackPressed();
                break;
            case R.id.fr_create_tvCancel:
                SoftInputHelper.hideSoftInput(getContext(), view);
                getActivity().onBackPressed();
                break;
            case R.id.fr_create_layout:
                Log.d(TAG, "onClick: background clicked! hiding softInput!");
                SoftInputHelper.hideSoftInput(getContext(), view);
                break;
        }
    }

    private void saveClicked() {
        if (FRAGMENT_TYPE == FragmentType.SCENES) {
            final Scene newScene = new Scene(-1, nameText.getText().toString());
            Log.d(TAG, "saveClicked: creating scene: " + newScene.toString());
            sceneDao.create(newScene);
        }
        else {
            final Shoot newShoot = new Shoot(-1, nameText.getText().toString(), sceneId);
            Log.d(TAG, "saveClicked: creating shoot: " + newShoot.toString());
            shootDao.create(newShoot);
        }
    }

}
