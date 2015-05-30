package com.example.krishnateja.fuzztest.utils.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.krishnateja.fuzztest.R;
import com.example.krishnateja.fuzztest.activities.ImageActivity;
import com.example.krishnateja.fuzztest.activities.WebViewActivity;
import com.example.krishnateja.fuzztest.models.DataModel;
import com.example.krishnateja.fuzztest.utils.AppConstants;
import com.example.krishnateja.fuzztest.utils.DownloadImageAsyncTask;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MainRecyclerViewAdapter.class.getSimpleName();
    private int mType;
    private ArrayList<DataModel> mTextDataModelArrayList;
    private ArrayList<DataModel> mImageDataModelArrayList;
    private Context mContext;
    private int mImageCount = 0;
    private int mTextCount = 0;

    @Override
    public MainRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AppConstants.FLAGS.TEXT_FLAG) {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_text, parent, false), viewType);
        } else {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false), viewType);
        }
    }

    public MainRecyclerViewAdapter(Context context, int type, ArrayList<DataModel> imageDataModelArrayList, ArrayList<DataModel> textDataModelArrayList) {
        mContext = context;
        mType = type;
        mImageDataModelArrayList = imageDataModelArrayList;
        mTextDataModelArrayList = textDataModelArrayList;
        if (mTextDataModelArrayList != null) {
            mTextCount = mTextDataModelArrayList.size();
        }
        if (mImageDataModelArrayList != null) {
            mImageCount = mImageDataModelArrayList.size();
        }

    }

    @Override
    public void onBindViewHolder(MainRecyclerViewAdapter.ViewHolder holder, final int position) {
        if (position < mTextCount) {
            holder.textView.setText(mTextDataModelArrayList.get(position).getData());
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra(AppConstants.BundleExtras.URL, "http://quizzes.fuzzstaging.com/quizzes/mobile/1");
                    mContext.startActivity(intent);
                }
            });
        } else {
            if(mTextCount!=0){
                FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500);
                holder.imageView.setLayoutParams(layoutParams);
            }
            if (mImageDataModelArrayList.get(position - mTextCount).getPath() != null) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(mImageDataModelArrayList.get(position - mTextCount).getPath(), bmOptions);
                holder.imageView.setImageBitmap(bitmap);
            } else {
                Log.d(TAG, "iamge path null->");
                holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_image));
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ImageActivity.class);
                    intent.putExtra(AppConstants.BundleExtras.URL, mImageDataModelArrayList.get(position - mTextCount).getPath());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mImageCount + mTextCount;
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

    @Override
    public int getItemViewType(int position) {
        if (position < mTextCount) {
            return AppConstants.FLAGS.TEXT_FLAG;
        } else {
            return AppConstants.FLAGS.IMAGE_FLAG;
        }
    }
}
