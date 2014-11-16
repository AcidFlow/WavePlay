package info.acidflow.waveplay.ui.controllers.explorer.fs;

import java.net.ConnectException;
import java.util.List;

import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.bus.events.fileexplorer.DirectoryListedEvent;
import info.acidflow.waveplay.bus.events.server.error.HttpErrorEvent;
import info.acidflow.waveplay.bus.events.server.error.NetworkErrorEvent;
import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.ui.views.interfaces.explorer.fs.FileExplorerView;

/**
 * Created by paul on 15/11/14.
 */
public abstract class AbstractFileExplorerController {

    private FileExplorerView mFileExplorerView;
    private String mRootDirectory;
    private EventBus mLocalBus;

    protected AbstractFileExplorerController( FileExplorerView view, EventBus bus ){
        super();
        mFileExplorerView = view;
        mLocalBus = bus;
    }

    public void openFile( String filePath ){
        mFileExplorerView.showCurrentlyPlaying();
    }

    public void getFilesFromDirectory( String directoryPath ){
        mRootDirectory = directoryPath;
        mFileExplorerView.showProgress();
    }


    public void onDirectoryFilesListed( List< GsonFile > files ){
        mFileExplorerView.hideProgress();
        mFileExplorerView.showData( files );
    }

    public void onDirectoryListingError(){

    }

    public abstract boolean onBackPressed();


    public String getCurrentRootDirectory(){
        return mRootDirectory;
    }

    public void unregisterFromLocalBus(){
        if( mLocalBus.isRegistered( this ) ) {
            mLocalBus.unregister(this);
        }
    }

    public void registerToLocalBus(){
        if( !mLocalBus.isRegistered( this ) ) {
            mLocalBus.register(this);
        }
    }

    public void onEventMainThread( DirectoryListedEvent event ){
        if( event.isSuccess ){
            onDirectoryFilesListed( event.files );
        }else{
            onDirectoryListingError();
        }
    }

    public void onEventMainThread( NetworkErrorEvent event ){
        if( event.error.getCause() instanceof ConnectException){
            mFileExplorerView.showServerUnreachableError();
        }else{
            mFileExplorerView.showNetworkError();
        }
    }

    public void onEventMainThread( HttpErrorEvent event ){
        mFileExplorerView.showHttpError();
    }
}
