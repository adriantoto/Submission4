package dicoding.adrian.submission4.favorite.MovieFavorite;

import java.util.ArrayList;

import dicoding.adrian.submission4.movie.MovieItems;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<MovieItems> movies);
}
