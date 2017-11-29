package dk.blackdarkness.g17.cphindustries.createfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.SceneViewActivity;

/**
 * Created by Thoma on 11/03/2017.
 */

public class CreateSceneFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CreateSceneFragment";

    private TextView submitSave, submitCancel;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_scene_layout, container, false);
        submitSave = view.findViewById(R.id.fr_create_shot_tvSave);
        submitCancel = view.findViewById(R.id.fr_create_shot_tvCancel);

        initLayout();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initLayout() {
        getActivity().setTitle("Edit Scene");

        //Save textView
        submitSave.setOnClickListener(this);

        //cancel textview
        submitCancel.setOnClickListener(this);


        ((SceneViewActivity)getActivity()).resetActionBar(true);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.fr_create_shot_tvSave:
                getActivity().onBackPressed();
                break;
            case R.id.fr_create_shot_tvCancel:
                getActivity().onBackPressed();
                break;

        }
    }
}
