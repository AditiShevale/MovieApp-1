package com.example.aditi.movieapp.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aditi on 20/2/18.
 */

public class MovieReview implements Parcelable {

    String movieReview;
    String author;

    public MovieReview(String movieReview, String author) {
        this.movieReview = movieReview;
        this.author = author;
    }

    public String getMovieReview() {
        return movieReview;
    }

    public void setMovieReview(String movieReview) {
        this.movieReview = movieReview;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movieReview);
        dest.writeString(this.author);
    }

    protected MovieReview(Parcel in) {
        this.movieReview = in.readString();
        this.author = in.readString();
    }

    public static final Parcelable.Creator<MovieReview> CREATOR = new
            Parcelable.Creator<MovieReview>() {
                @Override
                public MovieReview createFromParcel(Parcel source) {
                    return new MovieReview(source);
                }

                @Override
                public MovieReview[] newArray(int size) {
                    return new MovieReview[size];
                }
            };
}
