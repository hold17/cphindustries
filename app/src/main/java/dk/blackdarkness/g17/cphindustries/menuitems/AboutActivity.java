package dk.blackdarkness.g17.cphindustries.menuitems;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import dk.blackdarkness.g17.cphindustries.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "AboutActivity";
    private TextView linkSource, linkApi;
    private String linkSourceString, linkApiString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        this.linkSourceString = getString(R.string.link_source_repo);
        this.linkApiString = getString(R.string.link_api_repo);

        this.linkSource = findViewById(R.id.link_source);
        this.linkSource.setOnClickListener(this);
        this.linkSource.setText("Source: " + linkSourceString);

        this.linkApi = findViewById(R.id.link_api);
        this.linkApi.setText("API: " + linkApiString);
        this.linkApi.setOnClickListener(this);

        getLayoutInflater().inflate(R.layout.custom_toolbar_layout, findViewById(android.R.id.content));
        Toolbar toolbar = findViewById(R.id.customToolbar);
        toolbar.setTitle("About");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.link_source:
                goToURI(linkSourceString);
                Log.d(TAG, "onClick: R.id.link_source - Ran goToURI(" + linkSourceString +")");
                break;
            case R.id.link_api:
                goToURI(getString(R.string.link_api_repo));
                Log.d(TAG, "onClick: R.id.link_api - Ran goToURI(" + linkApiString +")");
                break;
        }
    }

    public void goToURI(String uri) {
        Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(openBrowser);
    }

}
