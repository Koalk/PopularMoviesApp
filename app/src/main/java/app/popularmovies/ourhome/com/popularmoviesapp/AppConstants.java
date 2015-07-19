package app.popularmovies.ourhome.com.popularmoviesapp;

/**
 * Created by Daniel on 19/07/2015.
 */
public class AppConstants {

    public static String API_BASE_URL = "http://api.themoviedb.org/3";
    public static String API_KEY_PARAMETER = "api_key";
    public static String API_KEY="53002f1d67cba08bc0078b440f5cf507";

    public static String API_DISCOVER_MID_URI ="/discover/movie";

    public static String API_SORT_PARAMETER="sort_by";
    public static String API_SORT_POPULAR="popularity";
    public static String API_SORT_RELEASE="release_date";
    public static String API_SORT_REVENUE="revenue";
    public static String API_SORT_PRIMRELEASE="primary_release_date";
    public static String API_SORT_TITLE="original_title";
    public static String API_SORT_VOTE_AVG="vote_average";
    public static String API_SORT_VOTE_CNT="vote_count";

    public static String API_SORT_ASC = ".asc";
    public static String API_SORT_DESC = ".desc";

    public static String API_IMAGE_MID_URI = "/movie/{id}/images";
}
