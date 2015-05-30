package com.example.krishnateja.fuzztest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.example.krishnateja.fuzztest.R;
import com.example.krishnateja.fuzztest.utils.AppConstants;
import com.example.krishnateja.fuzztest.utils.DownloadImageAsyncTask;


/**
 * Created by krishnateja on 5/29/2015.
 */
public class ImageActivity extends AppCompatActivity {

    private static final String TAG = ImageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        setUpToolBar();
        Intent intent = getIntent();
        String url = intent.getStringExtra(AppConstants.BundleExtras.URL);
        Log.d(TAG, "url");
        new DownloadImageAsyncTask(imageView, this).execute(url);
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
