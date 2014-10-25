package info.acidflow.waveplay.loaders;

import android.content.Context;
import android.os.Environment;

import java.util.List;

import info.acidflow.waveplay.exceptions.file.FileNotOnExternalStorageException;
import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.util.FileUtils;

/**
 * Created by paul on 16/10/14.
 */
public class FilesLoader extends WrappedAsyncTaskLoader< List< GsonFile > > {

    final public static int LOADER_ID = FilesLoader.class.hashCode();

    private String mRootDirectoryPath;

    public FilesLoader(Context context, String rootDir){
        super( context );
        if( rootDir == null ){
            mRootDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else {
            mRootDirectoryPath = rootDir;
        }
    }



    @Override
    public List<GsonFile> loadInBackground() {
        try {
            return FileUtils.listDirectory( mRootDirectoryPath );
        }catch ( FileNotOnExternalStorageException e ){
            e.handleException();
        }
        return null;
    }


}
