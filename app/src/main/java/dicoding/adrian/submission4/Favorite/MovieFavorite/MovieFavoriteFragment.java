package dicoding.adrian.submission4.Favorite.MovieFavorite;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import dicoding.adrian.submission4.Favorite.MovieFavorite.Adapter.MovieFavoriteAdapter;
import dicoding.adrian.submission4.Favorite.MovieFavorite.Database.MovieHelper;
import dicoding.adrian.submission4.Movie.DetailMovieActivity;
import dicoding.adrian.submission4.Movie.MovieItems;
import dicoding.adrian.submission4.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements LoadMoviesCallback {

    // Widgets, Array, Adapter, Helper Variable Declaration
    RecyclerView rvFavoriteMovies;
    ArrayList<MovieItems> movieItems;
    MovieFavoriteAdapter adapter;
    MovieHelper movieHelper;

    // Default Value
    private static final String EXTRA_STATE = "EXTRA_STATE";

    // Empty Constructor
    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // MovieItems Instance
        movieItems = new ArrayList<>();

        // Cast Recyclerview
        rvFavoriteMovies = view.findViewById(R.id.rv_movie_favorite);

        // Layout Manager
        rvFavoriteMovies.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // MovieHelper Instance
        movieHelper = new MovieHelper(getActivity());

        // Open MovieHelper
        movieHelper.open();

        // Adapter Instance
        adapter = new MovieFavoriteAdapter(getActivity());
        adapter.notifyDataSetChanged();

        // Set Adapter
        rvFavoriteMovies.setAdapter(adapter);

        // SavedInstanceState
        if (savedInstanceState == null) {
            new LoadMoviesAsync(movieHelper, this).execute();
        } else {
            ArrayList<MovieItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListMovie(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListMovies());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Loading Progress
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieItems> movies) {
        adapter.setListMovie(movies);
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<MovieItems>> {

        private final WeakReference<MovieHelper> weakNoteHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback callback) {
            weakNoteHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieItems> doInBackground(Void... voids) {
            return weakNoteHelper.get().getAllMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieItems> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == DetailMovieActivity.REQUEST_ADD) {
                if (resultCode == DetailMovieActivity.RESULT_ADD) {
                    MovieItems movieItems = data.getParcelableExtra(DetailMovieActivity.EXTRA_MOVIE);
                    adapter.addItem(movieItems);
                    rvFavoriteMovies.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
            } else if (requestCode == DetailMovieFavoriteActivity.REQUEST_UPDATE) {
                if (resultCode == DetailMovieFavoriteActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(DetailMovieFavoriteActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
