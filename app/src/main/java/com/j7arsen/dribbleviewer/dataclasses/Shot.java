package com.j7arsen.dribbleviewer.dataclasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by j7ars on 24.01.2017.
 */

public class Shot implements Parcelable {

    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("width")
    private int mWidth;
    @SerializedName("height")
    private int mHeight;
    @SerializedName("images")
    private ShotImages mImages;
    @SerializedName("views_count")
    private int mViewsCount;
    @SerializedName("likes_count")
    private int mLikesCount;
    @SerializedName("comments_count")
    private int mCommentsCount;
    @SerializedName("attachments_count")
    private int mAttachmentsCount;
    @SerializedName("rebounds_count")
    private int ReboundsCount;
    @SerializedName("buckets_count")
    private int mBucketsCount;
    @SerializedName("created_at")
    private Date mCreatedAt;
    @SerializedName("updated_at")
    private Date mUpdatedAt;
    @SerializedName("html_url")
    private String mHtmlUrl;
    @SerializedName("animated")
    private boolean mAnimated;
    @SerializedName("tags")
    private ArrayList<String> mTags;
    @SerializedName("user")
    private UserShot mUser;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public ShotImages getImages() {
        return mImages;
    }

    public void setImages(ShotImages images) {
        mImages = images;
    }

    public int getViewsCount() {
        return mViewsCount;
    }

    public void setViewsCount(int viewsCount) {
        mViewsCount = viewsCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getCommentsCount() {
        return mCommentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        mCommentsCount = commentsCount;
    }

    public int getAttachmentsCount() {
        return mAttachmentsCount;
    }

    public void setAttachmentsCount(int attachmentsCount) {
        mAttachmentsCount = attachmentsCount;
    }

    public int getReboundsCount() {
        return ReboundsCount;
    }

    public void setReboundsCount(int reboundsCount) {
        ReboundsCount = reboundsCount;
    }

    public int getBucketsCount() {
        return mBucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        mBucketsCount = bucketsCount;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        mHtmlUrl = htmlUrl;
    }

    public boolean isAnimated() {
        return mAnimated;
    }

    public void setAnimated(boolean animated) {
        mAnimated = animated;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
        dest.writeParcelable(this.mImages, flags);
        dest.writeInt(this.mViewsCount);
        dest.writeInt(this.mLikesCount);
        dest.writeInt(this.mCommentsCount);
        dest.writeInt(this.mAttachmentsCount);
        dest.writeInt(this.ReboundsCount);
        dest.writeInt(this.mBucketsCount);
        dest.writeLong(this.mCreatedAt != null ? this.mCreatedAt.getTime() : -1);
        dest.writeLong(this.mUpdatedAt != null ? this.mUpdatedAt.getTime() : -1);
        dest.writeString(this.mHtmlUrl);
        dest.writeByte(this.mAnimated ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.mTags);
        dest.writeParcelable(this.mUser, flags);
    }

    public Shot() {
    }

    protected Shot(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
        this.mImages = in.readParcelable(ShotImages.class.getClassLoader());
        this.mViewsCount = in.readInt();
        this.mLikesCount = in.readInt();
        this.mCommentsCount = in.readInt();
        this.mAttachmentsCount = in.readInt();
        this.ReboundsCount = in.readInt();
        this.mBucketsCount = in.readInt();
        long tmpMCreatedAt = in.readLong();
        this.mCreatedAt = tmpMCreatedAt == -1 ? null : new Date(tmpMCreatedAt);
        long tmpMUpdatedAt = in.readLong();
        this.mUpdatedAt = tmpMUpdatedAt == -1 ? null : new Date(tmpMUpdatedAt);
        this.mHtmlUrl = in.readString();
        this.mAnimated = in.readByte() != 0;
        this.mTags = in.createStringArrayList();
        this.mUser = in.readParcelable(UserShot.class.getClassLoader());
    }

    public static final Creator<Shot> CREATOR = new Creator<Shot>() {
        @Override
        public Shot createFromParcel(Parcel source) {
            return new Shot(source);
        }

        @Override
        public Shot[] newArray(int size) {
            return new Shot[size];
        }
    };
}
