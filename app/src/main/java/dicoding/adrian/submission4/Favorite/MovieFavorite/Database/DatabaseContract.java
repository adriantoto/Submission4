package dicoding.adrian.submission4.Favorite.MovieFavorite.Database;

import android.provider.BaseColumns;

class DatabaseContract {

    // To define some strings constant

    static String TABLE_MOVIE = "movie";

    static final class MovieColumns implements BaseColumns {
        static String TITLE = "title";
        static String POSTER = "poster";
        static String BACKDROP = "backdrop";
        static String OVERVIEW = "overview";
        static String RELEASED = "released";
        static String SCORE = "score";
    }
}
