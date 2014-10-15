package info.acidflow.waveplay.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * Created by paul on 15/10/14.
 */
public class FileUtils {

    final public static String LOG_TAG = FileUtils.class.getSimpleName();

    public static String guessMimeType( File file ) {
        String mime = null;
        if( !file.isDirectory() ) {
            mime = URLConnection.guessContentTypeFromName(file.getName());
            if (mime == null) {
                try {
                    InputStream is = new BufferedInputStream(new FileInputStream(file));
                    mime = URLConnection.guessContentTypeFromStream(is);
                    is.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "I/O Error", e);
                }
            }
        }
        return mime;
    }
}
