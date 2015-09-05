package app.popularmovies.ourhome.com.popularmoviesapp;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


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
        byte[] imageBitmap = (byte[]) ((Object[]) getActivity().getIntent().getExtras().get(FullScreenImageActivity.class.getName()))[0];
        Log.d(LOG_TAG, "Image bitmap: " + imageBitmap);
        Log.d(LOG_TAG, "Image view: " + image);
        image.setImageBitmap(BitmapFactory.decodeByteArray(imageBitmap, 0, imageBitmap.length));
        String title = (String) ((Object[]) getActivity().getIntent().getExtras().get(FullScreenImageActivity.class.getName()))[1];
        getActivity().getActionBar().setTitle(title);
        return rootView;
    }
}
