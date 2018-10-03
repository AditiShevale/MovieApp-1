package com.example.aditi.movieapp;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.aditi.movieapp.Adapter.Movie;
import com.example.aditi.movieapp.Adapter.RecyclerMovie;
import com.example.aditi.movieapp.Adapter.RecyclerMovieP;
import com.example.aditi.movieapp.Network.NetworkState;
import com.example.aditi.movieapp.Network.NetworkUtils;
import com.example.aditi.movieapp.ViewModel.MainViewModel;
import com.example.aditi.movieapp.model.Result;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class MainActivity extends AppCompatActivity implements
        RecyclerMovieP.ListItemClickListener {



    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "animal_image_transition_name";

    private RecyclerMovieP mRecyclerMovie;
    private MainViewModel viewModel;

    // onSaveinstance varibale

    private final static String MENU_SELECTED = "selected";
    private int selected = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);


        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
        mrecyclerView.setNestedScrollingEnabled(false);
        mRecyclerMovie = new RecyclerMovieP(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);



        /*viewModel.mLiveData().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                setupRecyclerView(results);
            }
        });*/

        viewModel.getData().observe(this, new Observer<PagedList<Result>>() {
            @Override
            public void onChanged(@Nullable PagedList<Result> results) {


                Log.d("pagelist", String.valueOf(results));
                mRecyclerMovie.submitList(results);
            }
        });

        viewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                mRecyclerMovie.setNetworkState(networkState);
            }
        });

        mrecyclerView.setAdapter(mRecyclerMovie);


       /* RemoteNetworkCall.getIntData().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                setupRecyclerView(results);
            }
        });*/


        //loadDefault("popular");
        //build("popular");

        //onSavedInstance loading if exist

       /* if (savedInstanceState != null) {
            selected = savedInstanceState.getInt(MENU_SELECTED);

            if (selected == -1) {

                loadDefault("top_rated");

            } else if (selected == R.id.highest_Rated) {

                loadDefault("popular");
            } else {

                loadDefault("top_rated");
            }

        }*/


    }

   /* private void setUpViewModel() {


        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        Object obj=viewModel.getAllMovies();


        //setupRecyclerView(viewModel.getAllMovies());


    }*/



    /*private void setupRecyclerView(List<Result> results) {

        if (results != null) {

            mRecyclerMovie = new RecyclerMovie(MainActivity.this, results, new RecyclerMovie.ListItemClickListener() {
                @Override
                public void onListItemClick(Result movie) {

                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("data", movie);
                    startActivity(intent);


                }
            });


            *//*mrecyclerView.setAdapter(mRecyclerMovie);
            mRecyclerMovie.notifyDataSetChanged();*//*
        } else {
            Toast.makeText(this, "List Null", Toast.LENGTH_SHORT).show();
        }


    }*/


    //onsaveInstanceState

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_SELECTED, selected);
        super.onSaveInstanceState(outState);
    }


    // For menu settings

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.highest_Rated:
                //build("top_rated");
                // loadDefault("popular");
                //  viewModel.getTopRated();
                selected = id;

                break;

            case R.id.most_popular:
                //build("popular");
                //loadDefault("top_rated");
                // viewModel.getPopular();
                selected = id;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(Result movie) {


        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("data", movie);
        startActivity(intent);
    }

    /*private URL build(String sort) {
        URL final_Url = NetworkUtils.buildURl(sort);
        new MovieDbQUeryTask().execute(final_Url);
        return final_Url;
    }*/
}
