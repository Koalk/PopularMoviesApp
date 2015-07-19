package app.popularmovies.ourhome.com.popularmoviesapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 19/07/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private final String LOG_TAG = ImageAdapter.class.getSimpleName();
    private Context mContext;
    private List<MoviePoster> moviePosterList;

    public ImageAdapter(Context c) {
        mContext = c;
        moviePosterList = new ArrayList<>();
    }

    public int getCount() {
        return moviePosterList.size();
    }

    public Object getItem(int position) {
        return moviePosterList.get(position);
    }

    public long getItemId(int position) {
        return Integer.parseInt(moviePosterList.get(position).getId());
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Log.v(LOG_TAG,"Showing image with id: "+this.moviePosterList.get(position).getId());
        Picasso.with(mContext).load(AppConstants.API_IMAGE_BASE_URI + this.moviePosterList.get(position).getUri()).into(imageView);
        return imageView;
    }

    public List<MoviePoster> getMoviePosterList() {
        return moviePosterList;
    }

    public void setMoviePosterList(List<MoviePoster> moviePosterList) {
        this.moviePosterList = moviePosterList;
    }
}
