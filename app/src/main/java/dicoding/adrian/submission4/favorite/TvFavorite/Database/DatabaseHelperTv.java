package dicoding.adrian.submission4.favorite.TvFavorite.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperTv extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContractTv.TABLE_TV,
            DatabaseContractTv.TvColumns._ID,
            DatabaseContractTv.TvColumns.TITLE_TV,
            DatabaseContractTv.TvColumns.POSTER_TV,
            DatabaseContractTv.TvColumns.BACKDROP_TV,
            DatabaseContractTv.TvColumns.OVERVIEW_TV,
            DatabaseContractTv.TvColumns.RELEASED_TV,
            DatabaseContractTv.TvColumns.SCORE_TV
    );

    DatabaseHelperTv(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContractTv.TABLE_TV);
        onCreate(db);
    }
}
