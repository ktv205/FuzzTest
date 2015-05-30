package com.example.krishnateja.fuzztest.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.krishnateja.fuzztest.R;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by krishnateja on 5/29/2015.
 */


public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    ImageView mImageView;
    Context mContext;

    public DownloadImageAsyncTask(ImageView bmImage, Context context) {
        mImageView = bmImage;
        mContext = context;
    }

    protected Bitmap doInBackground(String... urls) {
        return getBitmapFromURL(urls[0], new BitmapFactory.Options(), 100, 100);
    }

    public Bitmap getBitmapFromURL(final String imageUrl, final BitmapFactory.Options options, int reqWidth, int reqHeight) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(5000);
            connection.connect();
            final InputStream input = connection.getInputStream();
            // using byte array to prevent open 2 times a stream
            final BufferedInputStream bis = new BufferedInputStream(input, 4 * 1024);
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            bis.close();
            byte[] imageData = baf.toByteArray();
            if (options != null) {
                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options optionsSize = new BitmapFactory.Options();
                optionsSize.inJustDecodeBounds = true;


                BitmapFactory.decodeByteArray(imageData, 0, imageData.length, optionsSize);

                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(optionsSize, reqWidth, reqHeight);

                // Decode bitmap with inSampleSize set
                optionsSize.inJustDecodeBounds = false;

            }
            Bitmap myBitmap = null;
            if (options == null) {
                myBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            } else {
                myBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
            }
            // close the stream;
            input.close();
            return myBitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            mImageView.setImageBitmap(result);
        } else {
            mImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_image));
        }
    }

}

