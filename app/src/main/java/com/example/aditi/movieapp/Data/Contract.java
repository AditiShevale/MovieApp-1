package com.example.aditi.movieapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by aditi on 21/2/18.
 */

public class Contract {public static final String AUTHORITY = "com.example.natan.movietralierapp1";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);


    public static final String PATH_TASKS = "movies";


    public static final class Entry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "id";

        public static final String COLUMN_MOVIE_TITLE = "original_title";

        public static final String COLUMN_MOVIE_OVERVIEW = "overview";

        public static final String COLUMN_MOVIE_VOTE = "vote_average";

        public static final String COLUMN_POSTER_PATH = "posterpath";

        public static final String COLUMN_MOVIE_DATE = "release_date";


    }


}

