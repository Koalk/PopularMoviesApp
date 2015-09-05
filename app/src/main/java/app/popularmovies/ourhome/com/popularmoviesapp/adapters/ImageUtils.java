package app.popularmovies.ourhome.com.popularmoviesapp.adapters;

import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import app.popularmovies.ourhome.com.popularmoviesapp.AppConstants;

/**
 * Created by Daniel on 05/09/2015.
 */
public class ImageUtils {

    public static byte[] getImageByteArray(String url) {
        try {
            URL imageUrl = new URL(AppConstants.API_POSTER_BASE_URI + url);
            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }

            return baf.toByteArray();
        } catch (Exception e) {
            Log.d("ImageManager", "Error: " + e.toString());
        }
        return null;
    }
}
