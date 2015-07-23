package app.popularmovies.ourhome.com.popularmoviesapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 19/07/2015.
 */
public class AppConstants {

    public static String API_BASE_URL = "http://api.themoviedb.org/3";
    public static String API_POSTER_BASE_URI = "http://image.tmdb.org/t/p/w185";
    public static String API_DETAIL_IMAGE_BASE_URI = "http://image.tmdb.org/t/p/w342";
    public static String API_FULL_IMAGE_BASE_URI = "http://image.tmdb.org/t/p/original";
    public static String API_KEY_PARAMETER = "api_key";
    public static String API_KEY="";

    public static String API_DISCOVER_MID_URI ="/discover/movie";
    public static String API_DETAIL_MID_URI ="/movie";

    public static String API_SORT_PARAMETER="sort_by";
    public static String API_SORT_POPULAR="popularity";
    public static String API_SORT_REVENUE="revenue";
    public static String API_SORT_TITLE="original_title";
    public static String API_SORT_VOTE_AVG="vote_average";
    public static String API_SORT_VOTE_CNT="vote_count";

    public static List<String> SORT_OPTIONS;
    static {
        SORT_OPTIONS = new ArrayList<>();
        SORT_OPTIONS.add(API_SORT_POPULAR);
        SORT_OPTIONS.add(API_SORT_REVENUE);
        SORT_OPTIONS.add(API_SORT_TITLE);
        SORT_OPTIONS.add(API_SORT_VOTE_AVG);
        SORT_OPTIONS.add(API_SORT_VOTE_CNT);
    }

    public static String API_SORT_ASC = ".asc";
    public static String API_SORT_DESC = ".desc";
}
