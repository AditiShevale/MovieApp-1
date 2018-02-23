package com.example.aditi.movieapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aditi.movieapp.MainActivity;
import com.example.aditi.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aditi on 23/1/18.
 */

public class Recycler extends  RecyclerView.Adapter<Recycler.MyViewHolder> {

private List<Movie>mMovieList;
final private ListItemClickListener mOnClickListener;

    public Recycler(MainActivity mainActivity, List<Movie> movieList, ListItemClickListener onClickListener) {
        mMovieList = movieList;
        mOnClickListener = onClickListener;
    }


    public interface ListItemClickListener {
        void onListItemClick(Movie movie);

    }


    @Override
    public Recycler.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.custom_list,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Recycler.MyViewHolder holder, int position) {
        com.example.aditi.movieapp.Adapter.Movie movie = mMovieList.get(position);
        Context context = holder.movieImg.getContext();
        Picasso.with(context).load("https://image.tmdb.org/t/p/w185/"+movie.
                getImage()).into(holder.movieImg);
        Log.i("check","https://image.tmdb.org/t/p/w185/"+movie.getImage());
        holder.bind(mMovieList.get(position),mOnClickListener);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView movieImg;


        public MyViewHolder(View itemView) {
            super(itemView);
            movieImg =itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        public void bind(final Movie movie, final ListItemClickListener onClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onListItemClick(movie);

                }
            });

        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie posterClick = mMovieList.get(adapterPosition);
            mOnClickListener.onListItemClick(posterClick);
        }
    }
}
