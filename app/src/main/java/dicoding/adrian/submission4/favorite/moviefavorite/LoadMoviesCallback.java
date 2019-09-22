package dicoding.adrian.submission4.favorite.moviefavorite;

import android.database.Cursor;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(Cursor movies);
}
