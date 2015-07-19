package app.popularmovies.ourhome.com.popularmoviesapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class PosterGridFragment extends Fragment {

    private ImageAdapter imageAdapter;

    public PosterGridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_poster_grid,container,false);
        GridView postersView = (GridView) rootView.findViewById(R.id.grid_posters);
        imageAdapter = new ImageAdapter(getActivity());
        postersView.setAdapter(imageAdapter);
        postersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
        new PopularMoviesGridFetcher(this).execute();
        return rootView;
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    public void setImageAdapter(ImageAdapter imageAdapter) {
        this.imageAdapter = imageAdapter;
    }
}
