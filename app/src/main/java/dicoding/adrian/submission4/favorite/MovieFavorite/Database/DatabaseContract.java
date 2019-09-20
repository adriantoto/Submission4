package dicoding.adrian.submission4.favorite.MovieFavorite.Database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {

    public static final String AUTHORITY = "dicoding.adrian.submission4";
    private static final String SCHEME = "content";

    private DatabaseContract() {
    }

    public static final class MovieColumns implements BaseColumns {
        public static final String TABLE_MOVIE = "movie";
        public static final String TITLE = "title";
        public static final String POSTER = "poster";
        public static final String BACKDROP = "backdrop";
        public static final String OVERVIEW = "overview";
        public static final String RELEASED = "released";
        public static final String SCORE = "score";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }
}
