package app.popularmovies.ourhome.com.popularmoviesapp.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

import java.util.Calendar;

/**
 * Created by Daniel on 25/08/2015.
 */
public class PopularMoviesContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.ourhome.popularmovies.app";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE_DETAIL = "weather";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    public static final class MovieDetailEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_DETAIL).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_DETAIL;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_DETAIL;

        // Table name
        public static final String TABLE_NAME = "movie_detail";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_RELEASE = "release_date";
        public static final String COLUMN_FAVORITE = "favorite";
        public static final String COLUMN_POSTER = "poster";

        public static Uri buildMovieDetailUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static MovieDetail extractMovieDetail(Cursor cursor) {
            MovieDetail movieDetail = new MovieDetail();
            movieDetail.set_id(cursor.getLong(0));
            movieDetail.setTitle(cursor.getString(1));
            movieDetail.setSynopsis(cursor.getString(2));
            movieDetail.setVoteAverage(cursor.getString(3));
            Calendar releaseDate = Calendar.getInstance();
            releaseDate.setTimeInMillis(0);
            releaseDate.set(Calendar.YEAR, Integer.parseInt(cursor.getString(4).split(";")[0]));
            releaseDate.set(Calendar.MONTH, Integer.parseInt(cursor.getString(4).split(";")[1]));
            releaseDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(cursor.getString(4).split(";")[2]));
            movieDetail.setReleaseDate(releaseDate);
            movieDetail.setFavorite(cursor.getInt(5) == 1);
            movieDetail.setImage(cursor.getBlob(6));
            return movieDetail;
        }
    }
}
