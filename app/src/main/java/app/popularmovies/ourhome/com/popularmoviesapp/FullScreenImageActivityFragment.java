package app.popularmovies.ourhome.com.popularmoviesapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A placeholder fragment containing a simple view.
 */
public class FullScreenImageActivityFragment extends Fragment {

    private static final String LOG_TAG = FullScreenImageActivityFragment.class.getSimpleName();
    public FullScreenImageActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_full_screen_image, container, false);

        ImageView image = (ImageView) rootView.findViewById(R.id.fullscreen_image);
        String imageUri = ((String[]) getActivity().getIntent().getExtras().get(FullScreenImageActivity.class.getName()))[0];
        Log.d(LOG_TAG, "Image uri: " + imageUri);
        Log.d(LOG_TAG, "Image view: " + image);
        Picasso.with(getActivity()).load(AppConstants.API_FULL_IMAGE_BASE_URI + imageUri).into(image);
        String title = ((String[]) getActivity().getIntent().getExtras().get(FullScreenImageActivity.class.getName()))[1];
        getActivity().getActionBar().setTitle(title);
        return rootView;
    }
}
