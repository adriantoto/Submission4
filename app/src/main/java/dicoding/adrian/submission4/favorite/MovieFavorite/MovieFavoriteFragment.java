package dicoding.adrian.submission4.favorite.MovieFavorite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import dicoding.adrian.submission4.favorite.MovieFavorite.Adapter.MovieFavoriteAdapter;
import dicoding.adrian.submission4.features.contentprovider.MappingHelper;
import dicoding.adrian.submission4.movie.DetailMovieActivity;
import dicoding.adrian.submission4.movie.MovieItems;
import dicoding.adrian.submission4.R;

import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements LoadMoviesCallback {

    // Widgets, Array, Adapter, Helper Variable Declaration
    RecyclerView rvFavoriteMovies;
    MovieFavoriteAdapter adapter;

    // Default Value
    private static final String EXTRA_STATE = "EXTRA_STATE";

    // Empty Constructor
    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Cast Recyclerview
        rvFavoriteMovies = view.findViewById(R.id.rv_movie_favorite);

        // Layout Manager
        rvFavoriteMovies.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Divider between item list
        DividerItemDecoration itemDecorator = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.HORIZONTAL);
        itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider)));
        rvFavoriteMovies.addItemDecoration(itemDecorator);
        rvFavoriteMovies.setHasFixedSize(true);

        // Adapter Instance
        adapter = new MovieFavoriteAdapter(getActivity());

        // Set Adapter
        rvFavoriteMovies.setAdapter(adapter);

        // SavedInstanceState
        if (savedInstanceState == null) {
            new LoadMoviesAsync(Objects.requireNonNull(getActivity()).getApplicationContext(), this).execute();
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
    public void postExecute(Cursor movies) {
        ArrayList<MovieItems> listMovies = MappingHelper.mapCursorToArrayList(movies);
        if (listMovies.size() > 0) {
            adapter.setListMovie(listMovies);
        } else {
            adapter.setListMovie(new ArrayList<MovieItems>());
            Toast.makeText(getActivity(), "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(Context context, LoadMoviesCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
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
    }
}
