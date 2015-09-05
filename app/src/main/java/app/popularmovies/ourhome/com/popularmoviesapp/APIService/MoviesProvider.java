package app.popularmovies.ourhome.com.popularmoviesapp.APIService;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.Calendar;

import app.popularmovies.ourhome.com.popularmoviesapp.model.MovieDbHelper;
import app.popularmovies.ourhome.com.popularmoviesapp.model.MovieDetail;
import app.popularmovies.ourhome.com.popularmoviesapp.model.PopularMoviesContract;

/**
 * Created by Daniel on 25/08/2015.
 */
public class MoviesProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;
    private static final SQLiteQueryBuilder sMoviesQueryBuilder = new SQLiteQueryBuilder();
    static final int FAVORITES = 101;
    static final int LAST_VIEW = 102;

    public Cursor getMovieDetailByFavorite() {
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(), null, "favorite", new String[]{"1"}, null, null, null);
    }

    public Cursor getLastView() {
        return sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(), null, "favorite", new String[]{"0"}, null, null, null);
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = PopularMoviesContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE_DETAIL, FAVORITES);
        matcher.addURI(authority, PopularMoviesContract.PATH_MOVIE_DETAIL + "/*", LAST_VIEW);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        String result;
        switch (match) {
            case FAVORITES:
                result = PopularMoviesContract.MovieDetailEntry.CONTENT_TYPE;
                break;
            case LAST_VIEW:
                result = PopularMoviesContract.MovieDetailEntry.CONTENT_TYPE;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return result;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case FAVORITES:
                retCursor = getMovieDetailByFavorite();
                break;
            case LAST_VIEW:
                retCursor = getLastView();
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        normalizeDate(values);
        long _id = db.insert(PopularMoviesContract.MovieDetailEntry.TABLE_NAME, null, values);
        if (_id > 0)
            returnUri = PopularMoviesContract.MovieDetailEntry.buildMovieDetailUri(_id);
        else
            throw new android.database.SQLException("Failed to insert row into " + uri);
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    public long insert(MovieDetail movieDetail) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PopularMoviesContract.MovieDetailEntry.COLUMN_TITLE, movieDetail.getTitle());
        values.put(PopularMoviesContract.MovieDetailEntry.COLUMN_RELEASE, movieDetail.getReleaseDate().get(Calendar.YEAR) + ";" + movieDetail.getReleaseDate().get(Calendar.MONTH) + ";" + movieDetail.getReleaseDate().get(Calendar.DAY_OF_MONTH));
        values.put(PopularMoviesContract.MovieDetailEntry.COLUMN_FAVORITE, 1);
        values.put(PopularMoviesContract.MovieDetailEntry.COLUMN_POSTER, movieDetail.getImage());
        values.put(PopularMoviesContract.MovieDetailEntry.COLUMN_SYNOPSIS, movieDetail.getSynopsis());
        values.put(PopularMoviesContract.MovieDetailEntry.COLUMN_VOTE_AVERAGE, movieDetail.getVoteAverage());
        return db.insert(PopularMoviesContract.MovieDetailEntry.TABLE_NAME, null, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        rowsDeleted = db.delete(PopularMoviesContract.MovieDetailEntry.TABLE_NAME, PopularMoviesContract.MovieDetailEntry.COLUMN_TITLE, selectionArgs);
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(PopularMoviesContract.MovieDetailEntry.COLUMN_RELEASE)) {
            long dateValue = values.getAsLong(PopularMoviesContract.MovieDetailEntry.COLUMN_RELEASE);
            values.put(PopularMoviesContract.MovieDetailEntry.COLUMN_RELEASE, PopularMoviesContract.normalizeDate(dateValue));
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated;
        normalizeDate(values);
        rowsUpdated = db.update(PopularMoviesContract.MovieDetailEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        db.beginTransaction();
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                normalizeDate(value);
                long _id = db.insert(PopularMoviesContract.MovieDetailEntry.TABLE_NAME, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }
}
