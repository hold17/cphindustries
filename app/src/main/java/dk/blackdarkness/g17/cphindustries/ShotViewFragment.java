package dk.blackdarkness.g17.cphindustries;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Thoma on 11/02/2017.
 */

public class ShotViewFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ShotViewFragment";
    private Fragment weaponViewFragment;
    private Button test;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        test = (Button) view.findViewById(R.id.test2);
    }

    public void initLayout() {
        test.setText("Go to weapons view fragment.");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.test2:
                Log.d(TAG, "onClick: Trying to open weaponview fragment.");
                weaponViewFragment = new WeaponViewFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, weaponViewFragment).addToBackStack(null).commit();

        }
    }


}
