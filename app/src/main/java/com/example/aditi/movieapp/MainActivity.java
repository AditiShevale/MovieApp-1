package com.example.aditi.movieapp;


import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.aditi.movieapp.Adapter.Movie;
import com.example.aditi.movieapp.Adapter.Recycler;
import com.example.aditi.movieapp.Data.Contract;

import java.net.URL;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private Recycler mRecyclerMovie;
    private RecyclerView mrecyclerView;
    private ProgressBar mProgressBar;
    private static final int MOVIE_LOADER_ID = 1;
    private FavoriteAdapter mFavoritesAdapter;

    private final static String MENU_SELECTED = "selected";
    private int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configToasty();


        mrecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBar);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);

        mrecyclerView.setLayoutManager(mLayoutManager);
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (savedInstanceState == null) {

            build("popular");

        } else {
            if (savedInstanceState != null) {
                selected = savedInstanceState.getInt(MENU_SELECTED);

                if (selected == -1) {

                    build("popular");
                } else if (selected == R.id.highest_Rated) {
                    getActionBar().setTitle("HIGHEST RATED");
                    build("top_rated");
                } else if (selected == R.id.favorites) {

                    getActionBar().setTitle("YOUR FAVORITES !!");
                    getLoaderManager().restartLoader(MOVIE_LOADER_ID,
                            null, this);
                    mFavoritesAdapter = new FavoriteAdapter(new
                                                                    Recycler.ListItemClickListener() {
                                                                        @Override
                                                                        public void onListItemClick(Movie movie) {
                                                                            Intent intent = new Intent(
                                                                                    MainActivity.this,
                                                                                    Details.class);
                                                                            intent.putExtra("data", movie);
                                                                            startActivity(intent);
                                                                        }
                                                                    }, this);
                    mrecyclerView.setAdapter(mFavoritesAdapter);

                } else if (selected == R.id.most_popular) {
                    getActionBar().setTitle("MOST POPULAR");
                    build("popular");
                }

            }
        }


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof Recycler.MyViewHolder) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int id = (int) viewHolder.itemView.getTag();
                Log.i("id", String.valueOf(id));
                String stringId = Integer.toString(id);
                Uri uri = Contract.Entry.CONTENT_URI;
                uri = uri.buildUpon().appendPath(stringId).build();
                Log.i("uri", String.valueOf(uri));
                int rowsDeleted = getContentResolver().delete(uri, null, null);
                Log.i("rows", String.valueOf(rowsDeleted));
                getLoaderManager().restartLoader(MOVIE_LOADER_ID, null, MainActivity.this);


            }
        }).attachToRecyclerView(mrecyclerView);


    }


    @SuppressLint("StaticFieldLeak")
    @Override


    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {


            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {

                    deliverResult(mTaskData);
                } else {

                    forceLoad();
                }
            }


            @Override
            public Cursor loadInBackground() {
                try {
                    return getContentResolver().query(Contract.Entry.CONTENT_URI,
                            null,
                            null,
                            null,
                            Contract.Entry.COLUMN_MOVIE_ID);

                } catch (Exception e) {

                    e.printStackTrace();
                    return null;
                }
            }


            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mFavoritesAdapter.swapCursor(data);


    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mFavoritesAdapter.swapCursor(null);

    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public class MovieDbQUeryTask extends AsyncTask<URL, Void, List<Movie>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected List<Movie> doInBackground(URL... urls) {
            List<Movie> resultm = null;

            if (isOnline()) {
                List<Movie> result = Network.fetchMovieData(urls[0]);
                resultm = result;
                return resultm;
            }
            return resultm;
        }


        @Override
        protected void onPostExecute(List<Movie> movies) {

            if (isOnline() && movies != null) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mRecyclerMovie = new Recycler(MainActivity.this,
                        movies, new Recycler.ListItemClickListener() {
                    @Override
                    public void onListItemClick(Movie movie) {
                        Intent intent = new Intent(MainActivity.this,
                                Details.class);
                        intent.putExtra("data", movie);
                        startActivity(intent);

                    }
                });


                mrecyclerView.setAdapter(mRecyclerMovie);
                mRecyclerMovie.notifyDataSetChanged();
            } else {
                Toasty.warning(MainActivity.this, "Check Internet Connection!",
                        Toast.LENGTH_SHORT).show();
            }


        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(MENU_SELECTED, selected);
        super.onSaveInstanceState(outState);
    }


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
                if (isOnline()) {
                    getSupportActionBar().setTitle("HIGHEST RATED");
                    build("top_rated");
                    selected = id;
                } else {
                    Toasty.warning(MainActivity.this, "Check Your Internet Connection !!", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.most_popular:
                if (isOnline()) {
                    getSupportActionBar().setTitle("MOST POPULAR");
                    build("popular");
                    selected = id;
                } else {
                    Toasty.warning(MainActivity.this,
                            "Check Your Internet Connection !!",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.favorites:

                selected = id;

                getSupportActionBar().setTitle("YOUR FAVORITES");
                getLoaderManager().restartLoader(MOVIE_LOADER_ID,
                        null, this);
                mFavoritesAdapter = new FavoriteAdapter(new Recycler.
                        ListItemClickListener() {
                    @Override
                    public void onListItemClick(Movie movie) {
                        Intent intent = new Intent(
                                MainActivity.this,
                                Details.class);
                        intent.putExtra("data", movie);
                        startActivity(intent);
                    }
                }, this);
                mrecyclerView.setAdapter(mFavoritesAdapter);


        }

        return super.onOptionsItemSelected(item);
    }

    private URL build(String sort) {
        URL final_Url = Network.buildURl(sort);
        new MovieDbQUeryTask().execute(final_Url);
        return final_Url;
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void configToasty() {

        Toasty.Config.getInstance().

                setErrorColor(ContextCompat.getColor(this, R.color.error_color)).
                setInfoColor(ContextCompat.getColor(this, R.color.info_color)).
                setWarningColor(ContextCompat.getColor(this, R.color.warning_color)).
                setSuccessColor(ContextCompat.getColor(this, R.color.success_color))
                .apply();


    }

}
