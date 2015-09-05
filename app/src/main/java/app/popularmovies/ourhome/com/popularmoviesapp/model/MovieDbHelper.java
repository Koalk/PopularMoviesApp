package app.popularmovies.ourhome.com.popularmoviesapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 25/08/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "popularmovies.db";

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + PopularMoviesContract.MovieDetailEntry.TABLE_NAME + " IF NOT EXISTS (" +
                PopularMoviesContract.MovieDetailEntry._ID + " INTEGER PRIMARY KEY," +
                PopularMoviesContract.MovieDetailEntry.COLUMN_TITLE + " TEXT UNIQUE NOT NULL, " +
                PopularMoviesContract.MovieDetailEntry.COLUMN_SYNOPSIS + " TEXT, " +
                PopularMoviesContract.MovieDetailEntry.COLUMN_VOTE_AVERAGE + " TEXT, " +
                PopularMoviesContract.MovieDetailEntry.COLUMN_RELEASE + " TEXT " +
                PopularMoviesContract.MovieDetailEntry.COLUMN_FAVORITE + " INTEGER " +
                PopularMoviesContract.MovieDetailEntry.COLUMN_POSTER + " TEXT " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}