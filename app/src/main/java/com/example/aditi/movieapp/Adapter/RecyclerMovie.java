package com.example.aditi.movieapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aditi.movieapp.MainActivity;
import com.example.aditi.movieapp.Picasso.RoundedTransformation;
import com.example.aditi.movieapp.R;
import com.example.aditi.movieapp.model.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aditi on 23/1/18.
 */

public class RecyclerMovie extends  RecyclerView.Adapter<RecyclerMovie.MyViewHolder> {

    private List<Result> mMovieList;
    //Implementing on click listener
    final private ListItemClickListener mOnClickListener;

    //Interface

    public interface ListItemClickListener {

        void onListItemClick(Result movie);
    }


    public RecyclerMovie(MainActivity mainActivity, List<Result> movieList, ListItemClickListener listener) {
        mMovieList = movieList;
        mOnClickListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Result movie = mMovieList.get(position);

        Picasso.get().load("https://image.tmdb.org/t/p/w500" +
                movie.getPosterPath()).transform(new RoundedTransformation
                (14, 0)).into(holder.img_movie);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.imageView)
        ImageView img_movie;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            Result result = mMovieList.get(adapterPosition);
            mOnClickListener.onListItemClick(result);



        }
    }
}
