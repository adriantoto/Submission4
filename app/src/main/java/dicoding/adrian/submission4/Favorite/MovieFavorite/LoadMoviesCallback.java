package dicoding.adrian.submission4.Favorite.MovieFavorite;

import java.util.ArrayList;

import dicoding.adrian.submission4.Movie.MovieItems;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<MovieItems> movies);
}
