package dicoding.adrian.submission4.movie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import dicoding.adrian.submission4.favorite.moviefavorite.database.DatabaseContract;

import static android.provider.BaseColumns._ID;

public class MovieItems implements Parcelable {

    // POJO Properties
    private int id;
    private String title;
    private String released;
    private String overview;
    private String poster;
    private String backdrop;
    private double score;

    MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String released = object.getString("release_date");
            String overview = object.getString("overview");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");
            double score = object.getDouble("vote_average");
            this.id = id;
            this.title = title;
            this.released = released;
            this.overview = overview;
            this.poster = poster;
            this.backdrop = backdrop;
            this.score = score;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleased() {
        return released;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }

    public double getScore() {
        return score;
    }

    public String getBackdrop() {
        return backdrop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.released);
        dest.writeString(this.overview);
        dest.writeString(this.poster);
        dest.writeString(this.backdrop);
        dest.writeDouble(this.score);
    }

    public MovieItems() {
    }

    public MovieItems(int id, String title, String released, String poster, String overview, String backdrop, double score) {
        this.id = id;
        this.title = title;
        this.released = released;
        this.poster = poster;
        this.overview = overview;
        this.backdrop = backdrop;
        this.score = score;
    }

    public MovieItems(Cursor cursor) {
        this.id = DatabaseContract.getColumnInt(cursor, _ID);
        this.title = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.released = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.RELEASED);
        this.poster = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.POSTER);
        this.overview = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.backdrop = DatabaseContract.getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP);
        this.score = DatabaseContract.getColumnDouble(cursor, DatabaseContract.MovieColumns.SCORE);
    }

    private MovieItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.released = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.backdrop = in.readString();
        this.score = in.readDouble();
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}