package app.popularmovies.ourhome.com.popularmoviesapp;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private ImageView posterView;
    private Image poster;
    private TextView ratingView;
    private TextView releaseDateView;
    private TextView synopsisView;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent caller = getActivity().getIntent();
        String filmId = caller.getExtras().get(Intent.EXTRA_TEXT).toString();
        this.posterView = (ImageView) rootView.findViewById(R.id.movie_poster);
        this.posterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullScreenIntent = new Intent(v.getContext(),FullscreenActivity.class);

            }
        });
        this.ratingView = (TextView) rootView.findViewById(R.id.vote_average_value);
        this.releaseDateView = (TextView) rootView.findViewById(R.id.release_date_value);
        this.synopsisView = (TextView) rootView.findViewById(R.id.synopsis_text_view);
        new PopularMoviesDetailFetcher(this).execute(filmId);
        return rootView;
    }

    public ImageView getPosterView() {
        return posterView;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
    }

    public void setPosterView(ImageView posterView) {
        this.posterView = posterView;
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
}
