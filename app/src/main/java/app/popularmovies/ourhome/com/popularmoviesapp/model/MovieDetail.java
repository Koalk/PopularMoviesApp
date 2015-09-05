package app.popularmovies.ourhome.com.popularmoviesapp.model;

import java.util.Calendar;

/**
 * Created by Daniel on 23/07/2015.
 */
public class MovieDetail {

    private Long _id;
    private String title;
    private byte[] image;
    private Calendar releaseDate;
    private String voteAverage;
    private String synopsis;
    private boolean favorite;

    public MovieDetail() {}

    public MovieDetail(String title, byte[] image, Calendar releaseDate, String voteAverage, String synopsis) {
        this.title = title;
        this.image = image;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
