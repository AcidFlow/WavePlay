package info.acidflow.waveplay.util;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import info.acidflow.waveplay.exceptions.file.FileNotOnExternalStorageException;
import info.acidflow.waveplay.server.model.GsonFile;
import timber.log.Timber;

/**
 * Created by paul on 15/10/14.
 */
public class FileUtils {

    final public static String LOG_TAG = FileUtils.class.getSimpleName();

    /**
     * Try to guess the file mime type, first by checking the headers, then using the file extension
     * @param file the file
     * @return the mime type as a string or null
     */
    public static String guessMimeType( File file ) {
        String mime = null;
        if( !file.isDirectory() ) {
            try {
                if( file.canRead() ) {
                    InputStream is = new BufferedInputStream( new FileInputStream( file ) );
                    mime = URLConnection.guessContentTypeFromStream( is );
                    is.close();
                }
            } catch (IOException e) {
                Timber.e( e, "IOException when guessing mimtype for file %s", file.getName() );
            }
            if (mime == null) {
                try {
                    mime = URLConnection.guessContentTypeFromName(file.getName());
                }catch ( StringIndexOutOfBoundsException e ){
                    Timber.e( e, "Error when guessing mimtype from extension for file %s",
                            file.getName() );
                }
            }
        }
        return mime;
    }

    /**
     * List files and directories of directoryPath at 1-level
     * @param directoryPath the path of the root directory
     * @return a list of directories and files
     * @throws FileNotOnExternalStorageException if the directory to list is outside the external
     * storage
     */
    public static List< GsonFile > listDirectory( String directoryPath ) throws FileNotOnExternalStorageException {
        List< GsonFile > gsonFiles = new ArrayList<GsonFile>();
        if( isFileInExternalStorage( directoryPath ) ){
            File folder = new File( directoryPath );
            if( folder.exists() && folder.canRead() && folder.isDirectory()  ){
                File[] files = folder.listFiles();
                for( File f : files ){
                    if( f.canRead() ) {
                        gsonFiles.add( new GsonFile( f ) );
                    }
                }
            }
            return gsonFiles;
        }else{
            throw new FileNotOnExternalStorageException( "File is not on the external storage" );
        }
    }

    /**
     * Check whether a file is on the external storage or not
     * @param filePath the path to the file to check
     * @return true if the file is on the external storage, false otherwise
     */
    public static boolean isFileInExternalStorage( String filePath ){
        if( filePath == null || filePath.isEmpty()){
            return false;
        }
        File externalStorage = Environment.getExternalStorageDirectory();
        File current = new File( filePath );
        if( externalStorage.equals( current ) ) {
            return true;
        }else{
            return isFileInExternalStorage( current.getParent() );
        }
    }
}
