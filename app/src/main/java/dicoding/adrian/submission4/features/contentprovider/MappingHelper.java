package dicoding.adrian.submission4.features.contentprovider;

import android.database.Cursor;

import java.util.ArrayList;

import dicoding.adrian.submission4.movie.MovieItems;

import static android.provider.BaseColumns._ID;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.BACKDROP;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.OVERVIEW;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.POSTER;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.RELEASED;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.SCORE;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.TITLE;

public class MappingHelper {

    public static ArrayList<MovieItems> mapCursorToArrayList(Cursor moviesCursor) {

        ArrayList<MovieItems> moviesList = new ArrayList<>();

        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(TITLE));
            String released = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(RELEASED));
            String overview = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(OVERVIEW));
            String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(POSTER));
            String backdrop = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(BACKDROP));
            double score = moviesCursor.getDouble(moviesCursor.getColumnIndexOrThrow(SCORE));
            moviesList.add(new MovieItems(id, title, released, poster, overview, backdrop, score));
        }

        return moviesList;
    }
}
