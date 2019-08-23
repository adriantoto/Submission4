package dicoding.adrian.submission4.movie;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

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

        // Cast Layout
        final ConstraintLayout mainLayout = view.findViewById(R.id.container_fragment_movie);

        // Set The Movies
        mainViewModelMovie.setMovie();

        // Cast Widget
        final EditText etSearch = view.findViewById(R.id.search_movie);
        ImageButton btnSeach = view.findViewById(R.id.btn_search_movie);

        // On Click for Search Button
        btnSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Keyboard hide after click the button
                InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                // Movie input
                String movie = etSearch.getText().toString().trim();
                // Search Movie
                mainViewModelMovie.searchMovie(movie);
                progressBar.setVisibility(View.VISIBLE);
                // If input is empty
                if (TextUtils.isEmpty(movie)) {
                    Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        // Toolbar Declaration
        Toolbar toolbarMovie = view.findViewById(R.id.toolbar_movie);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarMovie);

        // Adapter Instance
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        // Cast Recyclerview
        rvMovie = view.findViewById(R.id.rv_movie);

        // Recyclerview Layout Manager
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Divider between item list
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
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
        // Toolbar Title
        Objects.requireNonNull(getActivity()).setTitle("");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }
}
