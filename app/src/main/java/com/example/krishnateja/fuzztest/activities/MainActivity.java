package com.example.krishnateja.fuzztest.activities;

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
    private static final String[] TAB_TITLES = {"Text", "Images"};
    private static final int COUNT = 2;
    private static final String TEXT = "text";
    private static final String IMAGE = "image";

    ArrayList<DataModel> textDataModelArrayList = new ArrayList<>();
    ArrayList<DataModel> imagesDataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        if(savedInstanceState==null) {

        }else{
            ViewPager pager = (ViewPager) findViewById(R.id.pager);
            ScreenSlidePagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);
            SlidingTabLayout tabs = (SlidingTabLayout) findViewById(R.id.activity_main_sliding_tabs);
            tabs.setDistributeEvenly(true);
            tabs.setViewPager(pager);
        }
    }

    public void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(AppConstants.BundleExtras.TEXT,textDataModelArrayList);
        outState.putParcelableArrayList(AppConstants.BundleExtras.IMAGE,imagesDataModelArrayList);
        super.onSaveInstanceState(outState);
    }

    public void executeAsyncTask() {
        RequestParams params = new RequestParams();
        Uri.Builder url = new Uri.Builder();
        url.scheme(AppConstants.ServerVariables.SCHEME)
                .authority(AppConstants.ServerVariables.AUTHORITY);
        for (int i = 0; i < AppConstants.ServerVariables.PATHS.length; i++) {
            url.appendPath(AppConstants.ServerVariables.PATHS[i]);
        }
        url.build();
        params.setURI(url.toString());
        params.setMethod(AppConstants.ServerVariables.METHOD);
        params.setContext(this);
        new DataAysncTask(this).execute(params);

    }

    @Override
    public void getDataModelArrayList(ArrayList<DataModel> dataModelArrayList) {
        Log.d(TAG, "hrer in getDataModelArrayList");
        for (DataModel dataModel : dataModelArrayList) {
            if (dataModel.getType().equals(IMAGE)) {
                imagesDataModelArrayList.add(dataModel);
                Log.d(TAG,"image url->"+dataModel.getData());
            } else {
                textDataModelArrayList.add(dataModel);
            }
        }
        setUpTabs();

    }

    public void setUpTabs(){
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
            MainFragment fragment=new MainFragment();
            Bundle dataBundle=new Bundle();
            if(position==AppConstants.FLAGS.TEXT_FLAG){
                dataBundle.putParcelableArrayList(AppConstants.BundleExtras.TEXT,textDataModelArrayList);
                dataBundle.putInt(AppConstants.BundleExtras.DATA_FLAG,AppConstants.FLAGS.TEXT_FLAG);
            }else if(position==AppConstants.FLAGS.IMAGE_FLAG){
                dataBundle.putParcelableArrayList(AppConstants.BundleExtras.IMAGE,imagesDataModelArrayList);
                dataBundle.putInt(AppConstants.BundleExtras.DATA_FLAG, AppConstants.FLAGS.IMAGE_FLAG);
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
