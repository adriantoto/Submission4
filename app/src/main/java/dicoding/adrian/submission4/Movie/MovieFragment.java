package dicoding.adrian.submission4.Movie;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Objects;

import dicoding.adrian.submission4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    // Variables
    RecyclerView rvMovie;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModelMovie mainViewModelMovie;

    // Empty Constructor
    public MovieFragment() {
        // Required empty public constructor
    }

    // Observer
    private Observer<ArrayList<MovieItems>> getMovies = new Observer<ArrayList<MovieItems>>() {
        @Override
        public void onChanged(ArrayList<MovieItems> movieItems) {
            if (movieItems != null) {
                adapter.setData(movieItems);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Adapter Instance
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        // Cast Recyclerview
        rvMovie = view.findViewById(R.id.rv_movie);

        // Layout Manager
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Divider between item list
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider)));
        rvMovie.addItemDecoration(itemDecorator);
        rvMovie.setHasFixedSize(true);

        //Set Adapter
        rvMovie.setAdapter(adapter);

        // Progress Bar Declaration
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

        //Intent to Detail Activity
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieItems movieItems) {
                // Define and Start Intent
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailMovieActivity.class);
                moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItems);
                Objects.requireNonNull(getActivity()).startActivity(moveWithObjectIntent);

                // Intent Transition Animation
                (getActivity()).overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainViewModel Instance
        mainViewModelMovie = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModelMovie.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observer
        mainViewModelMovie.getMovies().observe(Objects.requireNonNull(getActivity()), getMovies);

        // Display The Items
        mainViewModelMovie.setMovie();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unsubscribing The Observer
        mainViewModelMovie.getMovies().removeObserver(getMovies);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }
}
