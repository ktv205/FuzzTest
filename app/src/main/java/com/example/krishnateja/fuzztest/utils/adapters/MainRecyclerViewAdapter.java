package com.example.krishnateja.fuzztest.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krishnateja.fuzztest.R;
import com.example.krishnateja.fuzztest.activities.ImageActivity;
import com.example.krishnateja.fuzztest.activities.WebViewActivity;
import com.example.krishnateja.fuzztest.models.DataModel;
import com.example.krishnateja.fuzztest.utils.AppConstants;
import com.example.krishnateja.fuzztest.utils.DownloadImageAsyncTask;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MainRecyclerViewAdapter.class.getSimpleName();
    private int mType;
    private ArrayList<DataModel> mDataModelArrayList;
    private Context mContext;

    @Override
    public MainRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mType == AppConstants.FLAGS.TEXT_FLAG) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_text, parent, false), mType);
        } else {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false), mType);
        }
    }

    public MainRecyclerViewAdapter(Context context, int type, ArrayList<DataModel> dataModelArrayList) {
        mContext = context;
        mType = type;
        mDataModelArrayList = dataModelArrayList;
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewAdapter.ViewHolder holder, final int position) {
        if (mType == AppConstants.FLAGS.TEXT_FLAG) {
            holder.textView.setText(mDataModelArrayList.get(position).getData());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(AppConstants.BundleExtras.URL, "http://quizzes.fuzzstaging.com/quizzes/mobile/1");
                    mContext.startActivity(intent);
                }
            });
        } else {
            Log.d(TAG, "iamge counf->" + mDataModelArrayList.size());
            new DownloadImageAsyncTask(holder.imageView, mContext).execute(mDataModelArrayList.get(position).getData());
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra(AppConstants.BundleExtras.URL, mDataModelArrayList.get(position).getData());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView, int type) {
            super(itemView);
            if (type == AppConstants.FLAGS.TEXT_FLAG) {
                textView = (TextView) itemView.findViewById(R.id.text);
            } else if (type == AppConstants.FLAGS.IMAGE_FLAG) {
                imageView = (ImageView) itemView.findViewById(R.id.image);

            }
        }
    }


}
