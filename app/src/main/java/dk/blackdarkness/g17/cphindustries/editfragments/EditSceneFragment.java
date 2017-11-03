package dk.blackdarkness.g17.cphindustries.editfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditSceneFragment extends Fragment implements View.OnClickListener {
    private TextView dummy;
    private Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_scene_layout, container, false);
        dummy = view.findViewById(R.id.editSceneDummy);
        submit = view.findViewById(R.id.submitSceneEdit);
        initDisplay();
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Edit Scene #1");
        ((SceneViewActivity)getActivity()).resetActionBar(true);
        dummy.setText("Edit scene fragment");
        submit.setOnClickListener(this);
        submit.setText("Submit");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitSceneEdit:
                getActivity().onBackPressed();
        }
    }


}
