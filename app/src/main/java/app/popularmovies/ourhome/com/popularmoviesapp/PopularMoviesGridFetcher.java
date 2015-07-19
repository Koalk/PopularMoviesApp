package app.popularmovies.ourhome.com.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 19/07/2015.
 */
public class PopularMoviesGridFetcher extends AsyncTask<String,Void,List<MoviePoster>>{

    private final String LOG_TAG = PopularMoviesGridFetcher.class.getSimpleName();
    private String sortField;
    private String sortDirection;
    private PosterGridFragment fragment;

    public PopularMoviesGridFetcher (PosterGridFragment fragment){
        this.fragment = fragment;
    }

    @Override
    protected List<MoviePoster> doInBackground(String... params) {

        List<MoviePoster> moviePosterList = null;
        sortField = AppConstants.API_SORT_POPULAR;
        sortDirection = AppConstants.API_SORT_DESC;
        if (params.length > 0){
            sortField = params[0];
        }
        if (params.length > 1){
            sortDirection = params[1];
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String resultJsonString = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            Uri uri = Uri.parse(AppConstants.API_BASE_URL + AppConstants.API_DISCOVER_MID_URI).buildUpon()
                    .appendQueryParameter(AppConstants.API_SORT_PARAMETER, sortField+sortDirection)
                    .appendQueryParameter(AppConstants.API_KEY_PARAMETER, AppConstants.API_KEY).build();

            Log.v(LOG_TAG, "Formed query: " + uri.toString());
            URL url = new URL(uri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                resultJsonString = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                resultJsonString = null;
            }
            resultJsonString = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            resultJsonString = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        if (resultJsonString != null){
            try{
                moviePosterList = getMoviesPosters(resultJsonString);
            }
            catch (JSONException jsonException){
                Log.e(LOG_TAG,"Error parsing json response.");
            }
        }
        return moviePosterList;
    }

    private List<MoviePoster> getMoviesPosters(String movieListJsonStr)
            throws JSONException {
        JSONObject moviesJson = new JSONObject(movieListJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");
        List<MoviePoster> result = new ArrayList<>();
        for(int i = 0; i < moviesArray.length(); i++) {
            MoviePoster moviePoster = new MoviePoster();
            JSONObject movie = moviesArray.getJSONObject(i);
            moviePoster.setId(movie.getString("id"));
            moviePoster.setUri(movie.getString("poster_path"));
            result.add(moviePoster);
            Log.v(LOG_TAG, "Adding movie with poster: " + result.get(i).getUri());
        }
        return result;
    }

    @Override
    protected void onPostExecute(List<MoviePoster> moviePostersList) {
        if (moviePostersList != null){
            ImageAdapter imageAdapter = fragment.getImageAdapter();
            imageAdapter.getMoviePosterList().clear();
            imageAdapter.getMoviePosterList().addAll(moviePostersList);
            imageAdapter.notifyDataSetChanged();
        }
    }
}
