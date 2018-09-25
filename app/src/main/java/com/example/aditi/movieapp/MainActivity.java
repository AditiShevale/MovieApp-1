package com.example.aditi.movieapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.aditi.movieapp.Adapter.Movie;
import com.example.aditi.movieapp.Adapter.RecyclerMovie;
import com.example.aditi.movieapp.Data.Contract;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{


    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "animal_image_transition_name";

    private RecyclerMovie mRecyclerMovie;

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
        build("popular");

        //onSavedInstance loading if exist

        if (savedInstanceState != null) {
            selected = savedInstanceState.getInt(MENU_SELECTED);

            if (selected == -1) {
                build("popular");
            } else if (selected == R.id.highest_Rated) {
                build("top_rated");
            } else {
                build("popular");
            }

        }
    }


    //Creating inner class for Async Task

    public class MovieDbQUeryTask extends AsyncTask<URL, Void, List<Movie>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected List<Movie> doInBackground(URL... urls) {

            List<Movie> result = NetworkUtils.fetchMovieData(urls[0]);
            return result;
        }


        @Override
        protected void onPostExecute(List<Movie> movies) {


            mProgressBar.setVisibility(View.INVISIBLE);
            mRecyclerMovie = new RecyclerMovie(MainActivity.this, movies, new RecyclerMovie.ListItemClickListener() {
                @Override
                public void onListItemClick(Movie movie, ImageView imageView) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("data", movie);
                    intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageView));

                    startActivity(intent);

                }
            });

            mrecyclerView.setAdapter(mRecyclerMovie);
            mRecyclerMovie.notifyDataSetChanged();

        }
    }

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
                build("top_rated");
                selected = id;

                break;

            case R.id.most_popular:
                build("popular");
                selected = id;
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private URL build(String sort) {
        URL final_Url = NetworkUtils.buildURl(sort);
        new MovieDbQUeryTask().execute(final_Url);
        return final_Url;
    }
}
