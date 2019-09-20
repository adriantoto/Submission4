package dicoding.adrian.submission4.favorite.MovieFavorite;

import android.database.Cursor;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(Cursor movies);
}
