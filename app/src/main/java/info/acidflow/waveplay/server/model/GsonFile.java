package info.acidflow.waveplay.server.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paul on 13/10/14.
 */
public class GsonFile {

    @SerializedName("fileName")
    private String mFileName;

    @SerializedName("isDirectory")
    private boolean isDirectory;


    public GsonFile(String mFileName, boolean isDirectory) {
        this.mFileName = mFileName;
        this.isDirectory = isDirectory;
    }
}
