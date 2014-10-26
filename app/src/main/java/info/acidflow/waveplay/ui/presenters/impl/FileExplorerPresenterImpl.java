package info.acidflow.waveplay.ui.presenters.impl;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.util.List;

import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.ui.interactors.impl.FileExplorerInteractorLoader;
import info.acidflow.waveplay.ui.interactors.interfaces.FileExplorerInteractor;
import info.acidflow.waveplay.ui.presenters.interfaces.FileExplorerPresenter;
import info.acidflow.waveplay.ui.views.interfaces.FileExplorerView;

/**
 * Created by paul on 25/10/14.
 */
public class FileExplorerPresenterImpl extends Fragment implements FileExplorerPresenter{

    private FileExplorerInteractor mFileExplorerInteractor;
    private FileExplorerView mFileExplorerView;
    private String mRootDirectory;

    public static FileExplorerPresenterImpl newInstance( FileExplorerView view ){
        FileExplorerPresenterImpl f = new FileExplorerPresenterImpl();
        f.mFileExplorerView = view;
        f.mFileExplorerInteractor = FileExplorerInteractorLoader.newInstance( f );
        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        if( mFileExplorerInteractor instanceof Fragment ){
            getFragmentManager().beginTransaction()
                    .add((Fragment) mFileExplorerInteractor, String.valueOf(mFileExplorerInteractor.getClass().hashCode()))
                    .commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().beginTransaction()
                .remove((Fragment) mFileExplorerInteractor)
                .commitAllowingStateLoss();
    }

    @Override
    public void getFilesFromDirectory( String directoryPath ) {
        mRootDirectory = directoryPath;
        mFileExplorerView.showProgress();
        mFileExplorerInteractor.listFilesFromDirectory( directoryPath );
    }

    @Override
    public void openFile(String filePath) {
        mFileExplorerInteractor.openFile( filePath );
    }

    @Override
    public void onDirectoryFilesListed( List<GsonFile> files ) {
        mFileExplorerView.hideProgress();
        mFileExplorerView.showData( files );
    }

    @Override
    public void onInteractorReady() {
        mFileExplorerView.onPresenterReady();
    }

    @Override
    public String getCurrentRootDirectory() {
        return mRootDirectory;
    }

    @Override
    public boolean onBackPressed() {
        if( mRootDirectory == null || mRootDirectory.equals( Environment.getExternalStorageDirectory().getAbsolutePath() )){
            return false;
        }else{
            getFilesFromDirectory(new File(mRootDirectory).getParent());
            return true;
        }
    }

}
