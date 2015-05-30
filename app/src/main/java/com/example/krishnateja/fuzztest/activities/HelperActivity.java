package com.example.krishnateja.fuzztest.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krishnateja.fuzztest.R;
import com.example.krishnateja.fuzztest.models.DataModel;
import com.example.krishnateja.fuzztest.models.RequestParams;
import com.example.krishnateja.fuzztest.utils.AppConstants;
import com.example.krishnateja.fuzztest.utils.DataAysncTask;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class HelperActivity extends AppCompatActivity implements DataAysncTask.ServerDataInterface {
    private static final String TAG = HelperActivity.class.getSimpleName();
    ArrayList<DataModel> mTextDataModelArrayList = new ArrayList<>();
    ArrayList<DataModel> mImagesDataModelArrayList = new ArrayList<>();
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private boolean isFinished = false;
    private int mTotalCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        executeAsyncTask();
    }

    @Override
    public void getDataModelArrayList(ArrayList<DataModel> dataModelArrayList) {
        int i = 0;
        mTotalCount = dataModelArrayList.size();
        for (DataModel dataModel : dataModelArrayList) {
            if (dataModel.getType().equals(IMAGE)) {
                ImageClass imageClass = new ImageClass();
                imageClass.setDataModel(dataModel);
                new DownloadImageAsyncTask().execute(imageClass);
            } else {
                mTextDataModelArrayList.add(dataModel);
            }
        }


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


    public class DownloadImageAsyncTask extends AsyncTask<ImageClass, Void, ImageClass> {

        public DownloadImageAsyncTask() {

        }

        protected ImageClass doInBackground(ImageClass... params) {
            Bitmap bitmap = getBitmapFromURL(params[0].getDataModel().getData(), new BitmapFactory.Options(), 100, 100);
            params[0].setBitmap(bitmap);
            return params[0];
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

        @Override
        protected void onPostExecute(ImageClass imageClass) {
            if (imageClass.getBitmap() != null) {
                Log.d(TAG, "image not null");
                FileOutputStream out = null;
                String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                File file = new File(extStorageDirectory, imageClass.getDataModel().getId() + ".jpg");
                Log.d(TAG, "get absoulte path->" + file.getAbsolutePath());
                imageClass.getDataModel().setPath(file.getAbsolutePath());
                try {
                    out = new FileOutputStream(file);
                    imageClass.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                imageClass.getDataModel().setPath(null);
            }
            mImagesDataModelArrayList.add(imageClass.getDataModel());
            if (mTotalCount == mImagesDataModelArrayList.size() + mTextDataModelArrayList.size()) {
                Intent intent = new Intent(HelperActivity.this, MainActivity.class);
                intent.putExtra(AppConstants.BundleExtras.IMAGE, mImagesDataModelArrayList);
                intent.putExtra(AppConstants.BundleExtras.TEXT, mTextDataModelArrayList);
                startActivity(intent);
                finish();
            }


        }
    }

    public class ImageClass {
        private DataModel dataModel;
        private Bitmap bitmap;

        public DataModel getDataModel() {
            return dataModel;
        }

        public void setDataModel(DataModel dataModel) {
            this.dataModel = dataModel;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }


}
