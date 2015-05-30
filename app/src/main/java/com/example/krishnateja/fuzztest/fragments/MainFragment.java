package com.example.krishnateja.fuzztest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krishnateja.fuzztest.R;
import com.example.krishnateja.fuzztest.models.DataModel;
import com.example.krishnateja.fuzztest.utils.AppConstants;
import com.example.krishnateja.fuzztest.utils.adapters.MainRecyclerViewAdapter;
import com.example.krishnateja.fuzztest.utils.decorators.MainRecyclerViewDecorator;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainFragment.class.getSimpleName();
    private View mView;
    ArrayList<DataModel> mTextDataModelArrayList = new ArrayList<>();
    ArrayList<DataModel> mImagesDataModelArrayList = new ArrayList<>();
    private int mDataFlag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        Bundle bundle = getArguments();
        mDataFlag = bundle.getInt(AppConstants.BundleExtras.DATA_FLAG);
        if (mDataFlag == AppConstants.FLAGS.TEXT_FLAG) {
            mTextDataModelArrayList = bundle.getParcelableArrayList(AppConstants.BundleExtras.TEXT);
            setUpTextRecyclerView();
        } else if (mDataFlag == AppConstants.FLAGS.IMAGE_FLAG) {
            mImagesDataModelArrayList = bundle.getParcelableArrayList(AppConstants.BundleExtras.IMAGE);
            setUpImageRecyclerView();
        }
        return mView;

    }

    private void setUpImageRecyclerView() {
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), mDataFlag, mImagesDataModelArrayList);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_main_recycle_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(mainRecyclerViewAdapter);

    }

    private void setUpTextRecyclerView() {
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), mDataFlag, mTextDataModelArrayList);
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_main_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new MainRecyclerViewDecorator(getActivity()));
        recyclerView.setAdapter(mainRecyclerViewAdapter);

    }

    @Override
    public void onRefresh() {

    }
}
