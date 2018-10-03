package com.example.aditi.movieapp.PagingDataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import java.util.concurrent.Executor;

public class MovieDataFactory extends DataSource.Factory {


        MutableLiveData<MovieDataSource> mutableLiveData;
        MovieDataSource mMovieDataSource;
        Executor executor;


public MovieDataFactory(Executor executor) {
        this.mutableLiveData = new MutableLiveData<MovieDataSource>();
        this.executor = executor;
        }


@Override
public DataSource create() {
        mMovieDataSource = new MovieDataSource(executor);
        mutableLiveData.postValue(mMovieDataSource);
        return mMovieDataSource;
        }

public MutableLiveData<MovieDataSource> getMutableLiveData() {
        return mutableLiveData;
        }


        }
