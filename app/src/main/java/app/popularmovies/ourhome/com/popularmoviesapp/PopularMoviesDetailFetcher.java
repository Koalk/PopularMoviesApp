package app.popularmovies.ourhome.com.popularmoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Daniel on 19/07/2015.
 */
public class PopularMoviesDetailFetcher extends AsyncTask<String,Void,MovieDetail>{

    private final String LOG_TAG = PopularMoviesDetailFetcher.class.getSimpleName();
    private String filmId;
    private DetailActivityFragment fragment;

    public PopularMoviesDetailFetcher(DetailActivityFragment fragment){
        this.fragment = fragment;
    }

    @Override
    protected MovieDetail doInBackground(String... params) {

        MovieDetail movieDetail = null;
        if (params.length > 0){
            filmId = params[0];
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
            Uri uri = Uri.parse(AppConstants.API_BASE_URL + AppConstants.API_DETAIL_MID_URI + "/" + filmId).buildUpon()
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
                movieDetail = getMovieDetail(resultJsonString);
            }
            catch (JSONException jsonException){
                Log.e(LOG_TAG,"Error parsing json response.");
            }
        }
        return movieDetail;
    }

    private MovieDetail getMovieDetail(String movieDetailJson) throws JSONException {
        JSONObject movie = new JSONObject(movieDetailJson);
        String[] releaseDateStr = movie.getString("release_date").split("-");
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.setTimeInMillis(0);
        releaseDate.set(Calendar.YEAR,Integer.parseInt(releaseDateStr[0]));
        releaseDate.set(Calendar.MONTH,Integer.parseInt(releaseDateStr[1]));
        releaseDate.set(Calendar.DAY_OF_MONTH,Integer.parseInt(releaseDateStr[2]));
        MovieDetail result = new MovieDetail(
                movie.getString("title"),
                movie.getString("poster_path"),
                releaseDate,
                movie.getString("vote_average"),
                movie.getString("overview"));

        return result;
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        if (movieDetail != null){
            fragment.getActivity().getActionBar().setTitle(movieDetail.getTitle());
            Picasso.with(fragment.getActivity()).load(AppConstants.API_DETAIL_IMAGE_BASE_URI + movieDetail.getImage()).into(fragment.getPosterView());
            fragment.getRatingView().setText(movieDetail.getVoteAverage());
            fragment.getReleaseDateView().setText(movieDetail.getReleaseDate().get(Calendar.DAY_OF_MONTH) + "/"
                    + movieDetail.getReleaseDate().get(Calendar.MONTH) + "/"
                    + movieDetail.getReleaseDate().get(Calendar.YEAR) + "/");
            fragment.getSynopsisView().setText(movieDetail.getSynopsis());
        }
    }
}
