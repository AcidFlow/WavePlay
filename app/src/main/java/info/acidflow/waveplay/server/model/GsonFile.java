package info.acidflow.waveplay.server.model;

import com.google.gson.annotations.SerializedName;

import java.io.File;

import info.acidflow.waveplay.util.FileUtils;

/**
 * Created by paul on 13/10/14.
 */
public class GsonFile {

    final public static String LOG_TAG = GsonFile.class.getSimpleName();

    @SerializedName("fileName")
    private String mFileName;

    @SerializedName("filePath")
    private String mFilePath;

    @SerializedName("isDirectory")
    private boolean isDirectory;

    @SerializedName("mimeType")
    private String mMimeType;

    @SerializedName("childrenCount")
    private int mChildrenCount;


    public GsonFile( File file ) {
        mFilePath = file.getAbsolutePath();
        mFileName = file.getName() ;
        isDirectory = file.isDirectory() ;
        mMimeType = FileUtils.guessMimeType( file );
        if( file.isDirectory() ){
            mChildrenCount = file.list().length;
        }else{
            mChildrenCount = 0;
        }

    }

    public String getFileName() {
        return mFileName;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public int getChildrenCount() {
        return mChildrenCount;
    }
}
