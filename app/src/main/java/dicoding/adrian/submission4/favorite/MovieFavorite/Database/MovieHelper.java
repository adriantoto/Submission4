package dicoding.adrian.submission4.favorite.MovieFavorite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import dicoding.adrian.submission4.movie.MovieItems;

import static android.provider.BaseColumns._ID;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.BACKDROP;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.OVERVIEW;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.POSTER;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.RELEASED;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.SCORE;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.TITLE;
import static dicoding.adrian.submission4.favorite.MovieFavorite.Database.DatabaseContract.MovieColumns.TABLE_MOVIE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private static SQLiteDatabase database;

    public MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<MovieItems> query() {
        ArrayList<MovieItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        MovieItems movieItems;
        if (cursor.getCount() > 0) {
            do {
                movieItems = new MovieItems();
                movieItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieItems.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movieItems.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP)));
                movieItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movieItems.setReleased(cursor.getString(cursor.getColumnIndexOrThrow(RELEASED)));
                movieItems.setScore(cursor.getDouble(cursor.getColumnIndexOrThrow(SCORE)));

                arrayList.add(movieItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(MovieItems movieItems) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TITLE, movieItems.getTitle());
        initialValues.put(POSTER, movieItems.getPoster());
        initialValues.put(BACKDROP, movieItems.getBackdrop());
        initialValues.put(OVERVIEW, movieItems.getOverview());
        initialValues.put(RELEASED, movieItems.getReleased());
        initialValues.put(SCORE, movieItems.getScore());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(int id) {
        return database.delete(TABLE_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
