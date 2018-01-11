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
import dk.blackdarkness.g17.cphindustries.dataaccess.ApplicationConfig;
import dk.blackdarkness.g17.cphindustries.dto.Shoot;
import dk.blackdarkness.g17.cphindustries.dataaccess.ShootDao;

public class CreateShotFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "CreateShotFragment";
    private TextView submitSave, submitCancel;
    private EditText shootNameText;
    private int sceneId;
    private ShootDao shootDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_shot_layout, container, false);

        shootNameText = view.findViewById(R.id.fr_create_shot_editText);
        submitSave = view.findViewById(R.id.fr_create_shot_tvSave);
        submitCancel = view.findViewById(R.id.fr_create_shot_tvCancel);

        this.shootDao = ApplicationConfig.getDaoFactory().getShootDao();

        this.sceneId = getArguments().getInt("sceneId");

        initLayout();
        Log.d(TAG, "onCreateView: Returning.");
        return view;
    }

    public void initLayout() {
        getActivity().setTitle("Create Shot");

        //Save textView
        submitSave.setOnClickListener(this);

        //cancel textview
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
        }
    }

    private void saveClicked() {
        final Shoot newShoot = new Shoot(-1, this.shootNameText.getText().toString(), this.sceneId);
        System.out.println("Creating shoot: " + newShoot);
        this.shootDao.create(newShoot);
    }

}
