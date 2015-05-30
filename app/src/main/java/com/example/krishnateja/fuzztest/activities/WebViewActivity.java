package com.example.krishnateja.fuzztest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.example.krishnateja.fuzztest.R;
import com.example.krishnateja.fuzztest.utils.AppConstants;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class WebViewActivity extends AppCompatActivity {
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setUpToolBar();
        WebView webView = (WebView) findViewById(R.id.web_view);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String url = intent.getStringExtra(AppConstants.BundleExtras.URL);
            mUrl = url;
        } else {
            mUrl = savedInstanceState.getString(AppConstants.BundleExtras.URL);
        }
        webView.loadUrl(mUrl);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(AppConstants.BundleExtras.URL, mUrl);
        super.onSaveInstanceState(outState);
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
