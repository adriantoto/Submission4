package dicoding.adrian.submission4.Favorite.MovieFavorite.Database;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_MOVIE = "movie";

    static final class MovieColumns implements BaseColumns {
        static String TITLE = "title";
        static String POSTER = "poster";
        static String OVERVIEW = "overview";
    }
}
