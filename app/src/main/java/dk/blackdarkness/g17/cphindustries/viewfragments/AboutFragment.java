package dk.blackdarkness.g17.cphindustries.viewfragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;
import dk.blackdarkness.g17.cphindustries.activities.ViewSceneActivity;

public class AboutFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "AboutFragment";
    private TextView linkSource, linkApi;
    private String linkSourceString, linkApiString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_about, container, false);

        this.linkSourceString = getString(R.string.link_source_repo);
        this.linkApiString = getString(R.string.link_api);

        this.linkSource = v.findViewById(R.id.link_source);
        this.linkSource.setOnClickListener(this);
        this.linkSource.setText("Source: " + linkSourceString);

        this.linkApi = v.findViewById(R.id.link_api);
        this.linkApi.setText("API: " + linkApiString);
        this.linkApi.setOnClickListener(this);

        ((ViewSceneActivity)getActivity()).enableActionBar(true);
        ((ViewSceneActivity)getActivity()).setActionBarTitle("About");

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.link_source:
                goToURI(linkSourceString);
                Log.d(TAG, "onClick: R.id.link_source - Ran goToURI(" + linkSourceString +")");
                break;
            case R.id.link_api:
                goToURI(getString(R.string.link_api));
                Log.d(TAG, "onClick: R.id.link_api - Ran goToURI(" + linkApiString +")");
                break;
        }
    }

    public void goToURI(String uri) {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(openBrowser);
    }

}
