package com.example.aditi.movieapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import com.example.aditi.movieapp.Network.NetworkState;
import com.example.aditi.movieapp.Respository;
import com.example.aditi.movieapp.model.Result;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Result>> mData;
    private Respository mRespository;
    public LiveData<NetworkState> networkState;

    public LiveData<PagedList<Result>> userList;




    public MainViewModel(Application application) {
        super(application);





        mRespository = new Respository(application);
        userList=mRespository.getData();
        networkState=mRespository.getNetworkState();

    }


    public LiveData<PagedList<Result>> getData()
    {
        return userList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
