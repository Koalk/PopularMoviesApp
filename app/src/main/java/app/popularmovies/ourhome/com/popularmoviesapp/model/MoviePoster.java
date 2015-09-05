package app.popularmovies.ourhome.com.popularmoviesapp.model;

/**
 * Created by Daniel on 19/07/2015.
 */
public class MoviePoster {

    private String id;
    private String uri;
    private byte[] image;

    public MoviePoster(String id, String uri, byte[] image) {
        this.id = id;
        this.uri = uri;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
