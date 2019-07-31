package dicoding.adrian.submission4.Movie;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class MovieItems implements Parcelable {

    // POJO Properties
    private int id;
    private String title;
    private String released;
    private String overview;
    private String poster;
    private double score;

    MovieItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String released = object.getString("release_date");
            String overview = object.getString("overview");
            String poster = object.getString("poster_path");
            double score = object.getDouble("vote_average");
            this.id = id;
            this.title = title;
            this.released = released;
            this.overview = overview;
            this.poster = poster;
            this.score = score;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    String getReleased() {
        return released;
    }

    String getOverview() {
        return overview;
    }

    String getPoster() {
        return poster;
    }

    double getScore() {
        return score;
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
        dest.writeDouble(this.score);
    }

    private MovieItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.released = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
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