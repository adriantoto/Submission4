package dicoding.adrian.submission4.Favorite.TvFavorite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import dicoding.adrian.submission4.TV.TvItems;

import static android.provider.BaseColumns._ID;

import static dicoding.adrian.submission4.Favorite.TvFavorite.Database.DatabaseContractTv.TABLE_TV;
import static dicoding.adrian.submission4.Favorite.TvFavorite.Database.DatabaseContractTv.TvColumns.BACKDROP_TV;
import static dicoding.adrian.submission4.Favorite.TvFavorite.Database.DatabaseContractTv.TvColumns.OVERVIEW_TV;
import static dicoding.adrian.submission4.Favorite.TvFavorite.Database.DatabaseContractTv.TvColumns.POSTER_TV;
import static dicoding.adrian.submission4.Favorite.TvFavorite.Database.DatabaseContractTv.TvColumns.RELEASED_TV;
import static dicoding.adrian.submission4.Favorite.TvFavorite.Database.DatabaseContractTv.TvColumns.SCORE_TV;
import static dicoding.adrian.submission4.Favorite.TvFavorite.Database.DatabaseContractTv.TvColumns.TITLE_TV;

public class TvHelper {

    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelperTv dataBaseHelper;
    private static TvHelper INSTANCE;

    private static SQLiteDatabase database;

    public TvHelper(Context context) {
        dataBaseHelper = new DatabaseHelperTv(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
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

    public ArrayList<TvItems> getAllTvs() {
        ArrayList<TvItems> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvItems tvItems;
        if (cursor.getCount() > 0) {
            do {
                tvItems = new TvItems();
                tvItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvItems.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TV)));
                tvItems.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_TV)));
                tvItems.setBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROP_TV)));
                tvItems.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_TV)));
                tvItems.setReleased(cursor.getString(cursor.getColumnIndexOrThrow(RELEASED_TV)));
                tvItems.setScore(cursor.getDouble(cursor.getColumnIndexOrThrow(SCORE_TV)));

                arrayList.add(tvItems);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvItems tvItems) {
        ContentValues args = new ContentValues();
        args.put(TITLE_TV, tvItems.getTitle());
        args.put(POSTER_TV, tvItems.getPoster());
        args.put(BACKDROP_TV, tvItems.getBackdrop());
        args.put(OVERVIEW_TV, tvItems.getOverview());
        args.put(RELEASED_TV, tvItems.getReleased());
        args.put(SCORE_TV, tvItems.getScore());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(int id) {
        return database.delete(TABLE_TV, _ID + " = '" + id + "'", null);
    }
}
