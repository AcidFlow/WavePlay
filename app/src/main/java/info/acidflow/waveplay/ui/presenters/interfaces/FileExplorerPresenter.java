package info.acidflow.waveplay.ui.presenters.interfaces;

import java.util.List;

import info.acidflow.waveplay.server.model.GsonFile;

/**
 * Created by paul on 25/10/14.
 */
public interface FileExplorerPresenter {

    public void getFilesFromDirectory( String directoryPath );

    public void onDirectoryFilesListed( List< GsonFile > files );

    public void onInteractorReady();

    public boolean onBackPressed();

    public String getCurrentRootDirectory();

}
