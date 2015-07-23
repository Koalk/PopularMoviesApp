package app.popularmovies.ourhome.com.popularmoviesapp;

import java.util.Calendar;

/**
 * Created by Daniel on 23/07/2015.
 */
public class MovieDetail {
    private String title;
    private String image;
    private Calendar releaseDate;
    private String voteAverage;
    private String synopsis;

    public MovieDetail() {}

    public MovieDetail(String title, String image, Calendar releaseDate, String voteAverage, String synopsis) {
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

    public String getImage() {
        return image;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setImage(String image) {
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
}
