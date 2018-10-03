package com.example.aditi.movieapp;

import android.app.ActionBar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditi.movieapp.Picasso.RoundedTransformation;
import com.example.aditi.movieapp.model.Result;
import com.facebook.stetho.Stetho;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.release)
    TextView txt_Release;
    @BindView(R.id.title)
    TextView txt_Title;
    @BindView(R.id.image_poster)
    ImageView img_Poster;
    @BindView(R.id.plot)
    TextView txt_Plot;
    @BindView(R.id.app_bar_image)
    ImageView app_bar_img;
    @BindView(R.id.ratingbar)
    RatingBar mRatingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        postponeEnterTransition();

        Result movie = getIntent().getParcelableExtra("data");
        String name = getIntent().getExtras().getString(MainActivity.EXTRA_ANIMAL_IMAGE_TRANSITION_NAME);
        Float rating = Float.valueOf(movie.getVoteCount());
        Float cal = (5 * rating) / 10;

        mRatingbar.setRating(cal);

        img_Poster.setTransitionName(name);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getBackdropPath()).into(app_bar_img);
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath()).transform(new RoundedTransformation(20, 0)).into(img_Poster, new Callback() {
            @Override
            public void onSuccess() {
                startPostponedEnterTransition();
            }

            @Override
            public void onError(Exception e) {
                startPostponedEnterTransition();
            }
        });


        txt_Title.setText(movie.getTitle());
        txt_Plot.setText(movie.getOverview());
        txt_Release.setText(movie.getReleaseDate());


    }
}
