package dicoding.adrian.submission4.TV;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvItems implements Parcelable {

    // POJO Properties
    private int id;
    private String title;
    private String released;
    private String overview;
    private String poster;
    private String backdrop;
    private double score;

    TvItems(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("name");
            String released = object.getString("first_air_date");
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

    public void setId(int id) {
        this.id = id;
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

    public String getBackdrop() {
        return backdrop;
    }

    public double getScore() {
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
        dest.writeString(this.backdrop);
        dest.writeDouble(this.score);
    }

    public TvItems() {
    }

    private TvItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.released = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.backdrop = in.readString();
        this.score = in.readDouble();
    }

    public static final Parcelable.Creator<TvItems> CREATOR = new Parcelable.Creator<TvItems>() {
        @Override
        public TvItems createFromParcel(Parcel source) {
            return new TvItems(source);
        }

        @Override
        public TvItems[] newArray(int size) {
            return new TvItems[size];
        }
    };
}
