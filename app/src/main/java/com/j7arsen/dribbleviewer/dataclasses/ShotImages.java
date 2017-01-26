package com.j7arsen.dribbleviewer.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by j7ars on 24.01.2017.
 */

public class ShotImages implements Parcelable {

    @SerializedName("hidpi")
    private String mHiDpi;
    @SerializedName("normal")
    private String mNormal;
    @SerializedName("teaser")
    private String mTeaser;

    public ShotImages(String hiDpi, String normal, String teaser) {
        mHiDpi = hiDpi;
        mNormal = normal;
        mTeaser = teaser;
    }

    public String getHiDpi() {
        return mHiDpi;
    }

    public void setHiDpi(String mHiDpi) {
        this.mHiDpi = mHiDpi;
    }

    public String getNormal() {
        return mNormal;
    }

    public void setNormal(String mNormal) {
        this.mNormal = mNormal;
    }

    public String getTeaser() {
        return mTeaser;
    }

    public void setTeaser(String mTeaser) {
        this.mTeaser = mTeaser;
    }

    public String getImageUrl() {
        if (mHiDpi != null) {
            return mHiDpi;
        } else {
            if (mNormal != null) {
                return mNormal;
            } else {
                return mTeaser;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHiDpi);
        dest.writeString(this.mNormal);
        dest.writeString(this.mTeaser);
    }

    public ShotImages() {
    }

    protected ShotImages(Parcel in) {
        this.mHiDpi = in.readString();
        this.mNormal = in.readString();
        this.mTeaser = in.readString();
    }

    public static final Creator<ShotImages> CREATOR = new Creator<ShotImages>() {
        @Override
        public ShotImages createFromParcel(Parcel source) {
            return new ShotImages(source);
        }

        @Override
        public ShotImages[] newArray(int size) {
            return new ShotImages[size];
        }
    };
}
