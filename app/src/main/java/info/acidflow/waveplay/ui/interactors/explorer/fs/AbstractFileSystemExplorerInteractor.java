package info.acidflow.waveplay.ui.interactors.explorer.fs;

import de.greenrobot.event.EventBus;

/**
 * Created by paul on 15/11/14.
 */
public abstract  class AbstractFileSystemExplorerInteractor {

    private EventBus mLocalBus;

    protected AbstractFileSystemExplorerInteractor( EventBus localBus ) {
        super();
        mLocalBus = localBus;
    }

    abstract public void listFilesFromDirectory( String directoryPath );

    abstract public void openFile( String filePath );

    protected EventBus getLocalBus(){
        return mLocalBus;
    }

}
