package dicoding.adrian.submission4.Favorite.TvFavorite.Database;

import android.provider.BaseColumns;

class DatabaseContractTv {

    // To define some strings constant

    static String TABLE_TV = "tv";

    static final class TvColumns implements BaseColumns {
        static String TITLE_TV = "title_tv";
        static String POSTER_TV = "poster_tv";
        static String BACKDROP_TV = "backdrop_tv";
        static String OVERVIEW_TV = "overview_tv";
        static String RELEASED_TV = "released_tv";
        static String SCORE_TV = "score_tv";
    }
}
