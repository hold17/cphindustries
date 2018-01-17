package dk.blackdarkness.g17.cphindustries.createfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewMainActivity;
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dataaccess.SceneDao;
import dk.blackdarkness.g17.cphindustries.dto.Scene;
import dk.blackdarkness.g17.cphindustries.helper.softInputHelper;

public class CreateSceneFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private View view;
    private static final String TAG = "CreateSceneFragment";
    private TextView submitSave, submitCancel;
    private EditText sceneNameText;
    private SceneDao sceneDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_create_scene_layout, container, false);
        Log.d(TAG, "onCreateView: Returning.");

        submitSave = view.findViewById(R.id.fr_create_shot_tvSave);
        submitCancel = view.findViewById(R.id.fr_create_shot_tvCancel);
        sceneNameText = view.findViewById(R.id.fr_create_scene_editText_Scene);

        this.sceneDao = ApplicationConfig.getDaoFactory().getSceneDao();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((ViewMainActivity)getActivity()).setActionBarTitle("Create Scene");

        // handle softInput and focus
        sceneNameText.setFocusableInTouchMode(true);
        sceneNameText.requestFocus();
        softInputHelper.showSoftInput(getContext(), sceneNameText);
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
            case R.id.fr_create_scene_layout:
                softInputHelper.hideSoftInput(getContext(), view);
                break;
        }
    }

    private void saveClicked() {
        softInputHelper.hideSoftInput(getContext(), view);
        final Scene newScene = new Scene(-1, this.sceneNameText.getText().toString());
        Log.d(TAG, "saveClicked: creating scene: " + newScene.toString());
        this.sceneDao.create(newScene);
    }
}
