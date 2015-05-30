package com.example.krishnateja.fuzztest.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by krishnateja on 5/29/2015.
 */
public class DataModel implements Parcelable {
    private String id,type,date,data,path;

    public DataModel(Parcel source) {
        id=source.readString();
        type=source.readString();
        date=source.readString();
        data=source.readString();
        path=source.readString();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DataModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<DataModel> CREATOR = new Parcelable.Creator<DataModel>() {

        @Override
        public DataModel createFromParcel(Parcel source) {
            return new DataModel(source);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(type);
        dest.writeString(data);
        dest.writeString(date);
        dest.writeString(path);
    }
}
