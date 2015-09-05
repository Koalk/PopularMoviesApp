package app.popularmovies.ourhome.com.popularmoviesapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import app.popularmovies.ourhome.com.popularmoviesapp.APIService.PopularMoviesGridFetcher;
import app.popularmovies.ourhome.com.popularmoviesapp.adapters.ImageAdapter;
import app.popularmovies.ourhome.com.popularmoviesapp.model.MoviePoster;

public class PosterGridFragment extends Fragment {

    private ImageAdapter imageAdapter;

    public PosterGridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        Spinner spinner = (Spinner) menu.findItem(R.id.menuSort).getActionView();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sort_options, R.layout.sort_textview);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String orderPicked = AppConstants.SORT_OPTIONS.get(position);
                getUpdateTask().execute(orderPicked);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
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
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                MoviePoster moviePoster = (MoviePoster) imageAdapter.getItem(position);
                Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
                detailActivity.putExtra(Intent.EXTRA_TEXT, moviePoster.getId());
                startActivity(detailActivity);
            }
        });
        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    public void setImageAdapter(ImageAdapter imageAdapter) {
        this.imageAdapter = imageAdapter;
    }

    public PopularMoviesGridFetcher getUpdateTask() {
        return new PopularMoviesGridFetcher(this);
    }
}
