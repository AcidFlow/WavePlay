package info.acidflow.waveplay.ui.controllers.explorer.fs;

import android.os.Environment;

import java.io.File;

import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.ui.interactors.explorer.fs.AbstractFileSystemExplorerInteractor;
import info.acidflow.waveplay.ui.interactors.explorer.fs.NetworkFileSystemExplorerInteractor;
import info.acidflow.waveplay.ui.views.interfaces.explorer.fs.FileExplorerView;

/**
 * Created by paul on 15/11/14.
 */
public class NetworkFileSystemExplorerController extends AbstractFileExplorerController {

    private AbstractFileSystemExplorerInteractor mInteractor;

    public NetworkFileSystemExplorerController(FileExplorerView view, EventBus localBus, String ip,
        String port ) {
        super( view, localBus );
        mInteractor = new NetworkFileSystemExplorerInteractor( ip, port, localBus );
    }



    @Override
    public void getFilesFromDirectory( final String directoryPath ) {
        super.getFilesFromDirectory( directoryPath );
        new Thread(new Runnable() {
            @Override
            public void run() {
                mInteractor.listFilesFromDirectory( directoryPath );
            }
        }).start();
    }

    @Override
    public boolean onBackPressed() {
        if( getCurrentRootDirectory() == null || getCurrentRootDirectory().equals(Environment.getExternalStorageDirectory().getAbsolutePath())){
            return false;
        }else{
            getFilesFromDirectory(new File( getCurrentRootDirectory() ).getParent());
            return true;
        }
    }

    @Override
    public void openFile( final String filePath ) {
        super.openFile( filePath );
        new Thread(new Runnable() {
            @Override
            public void run() {
                mInteractor.openFile(filePath);
            }
        }).start();
    }
}
