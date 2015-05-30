package com.example.krishnateja.fuzztest.utils;

import android.util.Log;

import com.example.krishnateja.fuzztest.models.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class DataJsonParser {
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String DATE = "date";
    private static final String TEXT = "text";
    private static final String IMAGE = "image";
    private static final String TAG =DataJsonParser.class.getSimpleName() ;

    public static ArrayList<DataModel> parseServerData(String s) {
        ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
        try {
            JSONArray dataArray = new JSONArray(s);
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String type = "";
                if (dataObject.has(TYPE))
                    type = dataObject.getString(TYPE);
                String data = "";
                if (dataObject.has(DATA))
                    data = dataObject.getString(DATA);
               // Log.d(TAG, "has data");
                String date = "";
                if (dataObject.has(DATE))
                    date = dataObject.getString(DATE);
                String id = "";
                if (dataObject.has(ID))
                    id = dataObject.getString(ID);
                if ((type.equals(IMAGE) || type.equals(TEXT)) && !data.isEmpty()) {
                    DataModel dataModel = new DataModel();
                    dataModel.setType(type);
                    dataModel.setData(data);
                    dataModel.setDate(date);
                    dataModel.setId(id);
                    dataModelArrayList.add(dataModel);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataModelArrayList;

    }
}
