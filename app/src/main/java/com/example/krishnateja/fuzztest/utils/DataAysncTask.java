package com.example.krishnateja.fuzztest.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.example.krishnateja.fuzztest.models.DataModel;
import com.example.krishnateja.fuzztest.models.RequestParams;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class DataAysncTask extends AsyncTask<RequestParams, Void, String> {

    private Context mContext;
    private ServerDataInterface mServerDataInterface;

    public interface ServerDataInterface {
        public void getDataModelArrayList(ArrayList<DataModel> dataModelArrayList);
    }

    public DataAysncTask(Context context) {
        mContext = context;
        try {
            mServerDataInterface = (ServerDataInterface) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + " must implement" + ServerDataInterface.class.getSimpleName());
        }

    }


    @Override
    protected String doInBackground(RequestParams... params) {
        return HttpManager.sendUserData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        if(s!=null){
            ArrayList<DataModel> dataModelArrayList=DataJsonParser.parseServerData(s);
            mServerDataInterface.getDataModelArrayList(dataModelArrayList);
        }

    }
}
