package info.acidflow.waveplay.server.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import info.acidflow.waveplay.util.FileUtils;

/**
 * Created by paul on 13/10/14.
 */
public class GsonFile {

    final public static String LOG_TAG = GsonFile.class.getSimpleName();

    @SerializedName("fileName")
    private String mFileName;

    @SerializedName("isDirectory")
    private boolean isDirectory;

    @SerializedName("mimeType")
    private String mMimeType;


    public GsonFile( File file ) {
        mFileName = file.getName() ;
        isDirectory = file.isDirectory() ;
        mMimeType = FileUtils.guessMimeType( file );
    }


}
