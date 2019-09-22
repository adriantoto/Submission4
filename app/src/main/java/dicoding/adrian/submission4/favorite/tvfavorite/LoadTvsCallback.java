package dicoding.adrian.submission4.favorite.tvfavorite;

import java.util.ArrayList;

import dicoding.adrian.submission4.tv.TvItems;

public interface LoadTvsCallback {
    void preExecute();
    void postExecute(ArrayList<TvItems> tvs);
}
