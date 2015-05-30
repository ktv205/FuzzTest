package com.example.krishnateja.fuzztest.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        setUpToolBar();
        if(savedInstanceState==null) {
            Intent intent = getIntent();
            String url = intent.getStringExtra(AppConstants.BundleExtras.URL);
            mUrl=url;
        }else{
            mUrl=savedInstanceState.getString(AppConstants.BundleExtras.URL);
        }

        if(mUrl!=null) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(mUrl, bmOptions);
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.no_image));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(AppConstants.BundleExtras.URL,mUrl);
        super.onSaveInstanceState(outState);
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
