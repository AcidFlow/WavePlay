package info.acidflow.waveplay.ui.interactors.impl;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import info.acidflow.waveplay.loaders.FilesLoader;
import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.ui.interactors.interfaces.FileExplorerInteractor;
import info.acidflow.waveplay.ui.presenters.interfaces.FileExplorerPresenter;

/**
 * Created by paul on 25/10/14.
 */
public class FileExplorerInteractorLoader extends Fragment implements FileExplorerInteractor,
        LoaderManager.LoaderCallbacks<List<GsonFile>>{

    final public static String LOG_TAG = FileExplorerInteractorLoader.class.getSimpleName();
    final private static String FILE_LOADER_ROOT_DIR_ARG = "file_loader_root_dir_arg";

    private FileExplorerPresenter mPresenter;

    public static FileExplorerInteractorLoader newInstance( FileExplorerPresenter presenter ){
        FileExplorerInteractorLoader f = new FileExplorerInteractorLoader();
        f.mPresenter = presenter;
        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onInteractorReady();
    }

    @Override
    public Loader<List<GsonFile>> onCreateLoader(int i, Bundle bundle) {
        return new FilesLoader( getActivity(), bundle.getString( FILE_LOADER_ROOT_DIR_ARG, null ) );
    }

    @Override
    public void onLoadFinished(Loader<List<GsonFile>> listLoader, List<GsonFile> gsonFiles) {
        Log.i(LOG_TAG, "Loading done");
        if( gsonFiles == null ){
            Log.e(LOG_TAG, "Error occured during listing directory" );
        }else{
            Log.i(LOG_TAG, "List size : " + gsonFiles.size() );
            // TODO Set new data to the adapter
        }
        mPresenter.onDirectoryFilesListed( gsonFiles );
    }

    @Override
    public void onLoaderReset(Loader<List<GsonFile>> listLoader) {

    }

    @Override
    public void listFilesFromDirectory( String directoryPath ) {
        Bundle args = new Bundle();
        args.putString( FILE_LOADER_ROOT_DIR_ARG, directoryPath );
        if (getLoaderManager().getLoader(FilesLoader.LOADER_ID) == null) {
            getLoaderManager().initLoader(FilesLoader.LOADER_ID, args, this);
        } else {
            getLoaderManager().restartLoader(FilesLoader.LOADER_ID, args, this);
        }
    }

}
