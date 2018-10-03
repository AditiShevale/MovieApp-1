package com.example.aditi.movieapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.util.Log;

import com.example.aditi.movieapp.Network.NetworkState;
import com.example.aditi.movieapp.PagingDataSource.MovieDataFactory;
import com.example.aditi.movieapp.PagingDataSource.MovieDataSource;
import com.example.aditi.movieapp.model.Result;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Respository {

    public LiveData<PagedList<Result>> userList;
    public LiveData<NetworkState> networkState;
    Executor executor;
   LiveData<MovieDataSource> tDataSource;


    public Respository(Application application) {


        executor = Executors.newFixedThreadPool(5);
        MovieDataFactory movieDataFactory = new MovieDataFactory(executor);
        tDataSource = movieDataFactory.getMutableLiveData();

        networkState = Transformations.switchMap(movieDataFactory.getMutableLiveData(),
                dataSource -> {
            return dataSource.getNetworkState();
        });

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        userList = (new LivePagedListBuilder(movieDataFactory, pagedListConfig))
                .build();

        Log.d("pageData", String.valueOf(userList));


    }


    public LiveData<PagedList<Result>> getData() {
        return userList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }



}
