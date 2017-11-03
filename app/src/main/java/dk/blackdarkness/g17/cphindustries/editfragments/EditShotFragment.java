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

/**
 * Created by Thoma on 11/02/2017.
 */

public class EditShotFragment extends Fragment implements View.OnClickListener {

    private TextView dummy;
    private Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_shot_layout, container, false);
        dummy = view.findViewById(R.id.editShotDummy);
        submit = view.findViewById(R.id.submitShotEdit);
        initDisplay();
        return view;
    }

    public void initDisplay() {
        getActivity().setTitle("Edit Shot #1");
        dummy.setText("Edit shot fragment");
        submit.setOnClickListener(this);
        submit.setText("Submit");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submitShotEdit:
                getActivity().onBackPressed();
        }
    }

}
