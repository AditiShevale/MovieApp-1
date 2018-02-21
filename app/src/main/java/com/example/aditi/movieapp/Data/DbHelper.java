package com.example.aditi.movieapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aditi on 21/2/18.
 */

public class DbHelper extends SQLiteOpenHelper {


    //Name of the database
    private static final String DATABASE_NAME = "movieDb.db";

    //If database schema changed update the version
    private static final int VERSION = 1;

    //Constructor
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    //called when databse is first created

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String CREATE_TABLE = "CREATE TABLE " + Contract.Entry.TABLE_NAME +
                " (" +
                Contract.Entry._ID + " INTEGER PRIMARY KEY , " +
                Contract.Entry.COLUMN_MOVIE_ID + " TEXT NOT NULL , " +
                Contract.Entry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                Contract.Entry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                Contract.Entry.COLUMN_MOVIE_VOTE + " TEXT NOT NULL, " +
                Contract.Entry.COLUMN_MOVIE_DATE + " TEXT NOT NULL, " +
                Contract.Entry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +

                " UNIQUE (" + Contract.Entry.COLUMN_MOVIE_TITLE + ") ON CONFLICT REPLACE);";


        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Contract.Entry.TABLE_NAME);
        onCreate(db);

    }
}
