package com.example.aditi.movieapp.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aditi.movieapp.Data.Contract;
import com.example.aditi.movieapp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by aditi on 20/2/18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>{
    private Cursor mCursor;
    private Context mContext;
    private Recycler.ListItemClickListener mListItemClickListener;


    public FavoriteAdapter(Recycler.ListItemClickListener clickListener, Context context) {
        this.mListItemClickListener = clickListener;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.custom_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(Contract.Entry._ID);
        int posterIndex = mCursor.getColumnIndex(Contract.Entry.COLUMN_POSTER_PATH);
        int MovieIdIndex=mCursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_ID);
        mCursor.moveToPosition(position);
        final int id=mCursor.getInt(MovieIdIndex);
        holder.itemView.setTag(id);
        String imgUrl = mCursor.getString(posterIndex);
        Log.i("tagu", imgUrl);
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + imgUrl).
                into(holder.img_movie);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img_movie;

        public MyViewHolder(View itemView) {
            super(itemView);

            img_movie = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCursor.moveToPosition(getAdapterPosition());

            String id = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_ID));
            String posterUrl = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_POSTER_PATH));
            String title = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_TITLE));
            String release_date = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_DATE));
            String vote = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_VOTE));
            String overview = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_MOVIE_OVERVIEW));

            Movie movie = new Movie(id, posterUrl, title, release_date, vote, overview);
            mListItemClickListener.onListItemClick(movie);
        }
    }
}
