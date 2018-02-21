package com.example.aditi.movieapp.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aditi on 20/2/18.
 */

public class MovieTrailer implements Parcelable {


    String trailer_key;

    public MovieTrailer(String trailer_key) {
        this.trailer_key = trailer_key;
    }

    public String getTrailer_key() {
        return trailer_key;
    }

    public void setTrailer_key(String trailer_key) {
        this.trailer_key = trailer_key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.trailer_key);
    }

    protected MovieTrailer(Parcel in) {

        this.trailer_key = in.readString();
    }

    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel source) {
            return new MovieTrailer(source);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
}

