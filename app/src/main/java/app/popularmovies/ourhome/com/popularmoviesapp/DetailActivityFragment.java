package app.popularmovies.ourhome.com.popularmoviesapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.popularmovies.ourhome.com.popularmoviesapp.APIService.MoviesProvider;
import app.popularmovies.ourhome.com.popularmoviesapp.APIService.PopularMoviesDetailFetcher;
import app.popularmovies.ourhome.com.popularmoviesapp.model.MovieDetail;
import app.popularmovies.ourhome.com.popularmoviesapp.model.PopularMoviesContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private String filmId;
    private ImageView posterView;
    private byte[] posterBitmap;
    private TextView ratingView;
    private TextView releaseDateView;
    private TextView synopsisView;
    private MenuItem starButton;
    private MovieDetail movieDetail;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent caller = getActivity().getIntent();
        if (filmId == null){
            filmId = caller.getExtras().get(Intent.EXTRA_TEXT).toString();
        }
        this.posterView = (ImageView) rootView.findViewById(R.id.movie_poster);
        this.posterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG,"Showing fullscreen");
                Intent fullScreenIntent = new Intent(v.getContext(),FullScreenImageActivity.class);
                fullScreenIntent.putExtra(FullScreenImageActivity.class.getName(), new Object[]{posterBitmap, getActivity().getActionBar().getTitle().toString(), filmId});
                startActivity(fullScreenIntent);
            }
        });
        this.ratingView = (TextView) rootView.findViewById(R.id.vote_average_value);
        this.releaseDateView = (TextView) rootView.findViewById(R.id.release_date_value);
        this.synopsisView = (TextView) rootView.findViewById(R.id.synopsis_text_view);
        new PopularMoviesDetailFetcher(this).execute(filmId);
        starButton = (MenuItem) rootView.findViewById(R.id.favorite);
        starButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                MoviesProvider moviesProvider = new MoviesProvider();
                if (getMovieDetail().getFavorite()) {
                    moviesProvider.delete(PopularMoviesContract.MovieDetailEntry.CONTENT_URI, PopularMoviesContract.MovieDetailEntry._ID + "=" + getMovieDetail().get_id(), null);
                } else {
                    getMovieDetail().set_id(moviesProvider.insert(getMovieDetail()));
                }
                return false;
            }
        });
        return rootView;
    }

    public ImageView getPosterView() {
        return posterView;
    }

    public void setPosterView(ImageView posterView) {
        this.posterView = posterView;
    }

    public byte[] getPosterBitmap() {
        return posterBitmap;
    }

    public void setPosterBitmap(byte[] posterBitmap) {
        this.posterBitmap = posterBitmap;
    }

    public TextView getRatingView() {
        return ratingView;
    }

    public void setRatingView(TextView ratingView) {
        this.ratingView = ratingView;
    }

    public TextView getReleaseDateView() {
        return releaseDateView;
    }

    public void setReleaseDateView(TextView releaseDateView) {
        this.releaseDateView = releaseDateView;
    }

    public TextView getSynopsisView() {
        return synopsisView;
    }

    public void setSynopsisView(TextView synopsisView) {
        this.synopsisView = synopsisView;
    }

    public MenuItem getStarButton() {
        return starButton;
    }

    public void setStarButton(MenuItem starButton) {
        this.starButton = starButton;
    }

    public MovieDetail getMovieDetail() {
        return movieDetail;
    }

    public void setMovieDetail(MovieDetail movieDetail) {
        this.movieDetail = movieDetail;
    }
}
