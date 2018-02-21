package com.example.aditi.movieapp.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aditi on 27/1/18.
 */

public class Movie implements Parcelable {
    private String mTitle, mReleaseDate,mOverview,mImage,mVoteAverage;


    public Movie(String image){
        mImage = image;
    }



    public Movie(String image, String title, String releaseDate, String voteAverage,
                 String overview){

        mImage = image;
        mTitle =title;
        mOverview=overview;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;

    }

    public Movie(String id, String posterUrl, String title, String release_date, String vote, String overview) {

    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.mTitle);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mOverview);
        dest.writeString(this.mImage);
        dest.writeString(this.mVoteAverage);
    }

    protected Movie(Parcel in) {
        this.mTitle = in.readString();
        this.mReleaseDate = in.readString();
        this.mOverview = in.readString();
        this.mImage = in.readString();
        this.mVoteAverage = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
