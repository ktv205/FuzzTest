package com.example.krishnateja.fuzztest.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.krishnateja.fuzztest.R;
import com.example.krishnateja.fuzztest.fragments.MainFragment;
import com.example.krishnateja.fuzztest.models.DataModel;
import com.example.krishnateja.fuzztest.models.RequestParams;
import com.example.krishnateja.fuzztest.utils.AppConstants;
import com.example.krishnateja.fuzztest.utils.DataAysncTask;
import com.example.krishnateja.fuzztest.utils.tabs.SlidingTabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements DataAysncTask.ServerDataInterface {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] TAB_TITLES = {"All", "Text", "Images"};
    private static final int COUNT = 3;
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String ALL = "all";

    ArrayList<DataModel> mTextDataModelArrayList = new ArrayList<>();
    ArrayList<DataModel> mImagesDataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        if (savedInstanceState == null) {
            Log.d(TAG, "savedInstance is null");
            Intent intent = getIntent();
            mTextDataModelArrayList = intent.getParcelableArrayListExtra(AppConstants.BundleExtras.TEXT);
            mImagesDataModelArrayList = intent.getParcelableArrayListExtra(AppConstants.BundleExtras.IMAGE);
        } else {
            mImagesDataModelArrayList = savedInstanceState.getParcelableArrayList(AppConstants.BundleExtras.IMAGE);
            mTextDataModelArrayList = savedInstanceState.getParcelableArrayList(AppConstants.BundleExtras.TEXT);

        }
        setUpTabs();
    }

    public void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(AppConstants.BundleExtras.TEXT, mTextDataModelArrayList);
        outState.putParcelableArrayList(AppConstants.BundleExtras.IMAGE, mImagesDataModelArrayList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void getDataModelArrayList(ArrayList<DataModel> dataModelArrayList) {
        Log.d(TAG, "hrer in getDataModelArrayList");
        for (DataModel dataModel : dataModelArrayList) {
            if (dataModel.getType().equals(IMAGE)) {
                mImagesDataModelArrayList.add(dataModel);
                Log.d(TAG, "image url->" + dataModel.getData());
            } else {
                mTextDataModelArrayList.add(dataModel);
            }
        }
        setUpTabs();

    }

    public void setUpTabs() {
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.activity_main_sliding_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            MainFragment fragment = new MainFragment();
            Bundle dataBundle = new Bundle();
            if (position == AppConstants.FLAGS.TEXT_FLAG) {
                dataBundle.putParcelableArrayList(AppConstants.BundleExtras.TEXT, mTextDataModelArrayList);
            } else if (position == AppConstants.FLAGS.IMAGE_FLAG) {
                dataBundle.putParcelableArrayList(AppConstants.BundleExtras.IMAGE, mImagesDataModelArrayList);
            } else {
                dataBundle.putParcelableArrayList(AppConstants.BundleExtras.TEXT, mTextDataModelArrayList);
                dataBundle.putParcelableArrayList(AppConstants.BundleExtras.IMAGE, mImagesDataModelArrayList);
            }
            fragment.setArguments(dataBundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }
    }


}
