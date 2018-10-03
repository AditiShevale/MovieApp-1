package com.example.aditi.movieapp.PagingDataSource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.aditi.movieapp.Network.NetworkState;
import com.example.aditi.movieapp.Service.ApiClient;
import com.example.aditi.movieapp.Service.ApiInterface;
import com.example.aditi.movieapp.model.Example;
import com.example.aditi.movieapp.model.Result;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Result> {

    private static final String TAG = MovieDataSource.class.getSimpleName();


    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;
    ApiInterface mApiInterface;

    public MovieDataSource(Executor retryExecutor) {
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        this.retryExecutor = retryExecutor;
        mApiInterface = ApiClient.getClient();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Result> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        mApiInterface.fetchMoviesPaging("popular", ApiClient.api_key, 1, params.requestedLoadSize)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful()) {
                            String uri = call.request().url().toString();
                            List<Result> results = response.body().getResults();
                            Log.d("urlInitial", uri);
                            callback.onResult(results, null, 2l);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);
                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }


                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Log.d("urlInitial", "failure");

                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Result> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Result> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);


        mApiInterface.fetchMoviesPaging("popular", ApiClient.api_key, params.key, params.requestedLoadSize)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful()) {
                            long nextKey = (params.key == response.body().getTotalResults()) ? null : params.key + 1;
                            String uri = call.request().url().toString();

                            Log.d("urlAfter", uri);
                            callback.onResult(response.body().getResults(), nextKey);
                            networkState.postValue(NetworkState.LOADED);

                        } else
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });


    }
}

