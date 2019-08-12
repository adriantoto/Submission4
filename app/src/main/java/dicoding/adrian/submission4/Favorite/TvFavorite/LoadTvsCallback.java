package dicoding.adrian.submission4.Favorite.TvFavorite;

import java.util.ArrayList;

import dicoding.adrian.submission4.TV.TvItems;

public interface LoadTvsCallback {
    void preExecute();
    void postExecute(ArrayList<TvItems> tvs);
}
