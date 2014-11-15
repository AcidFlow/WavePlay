package info.acidflow.waveplay.ui.interactors.explorer.fs;

import android.os.Environment;

import java.util.List;

import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.bus.events.fileexplorer.DirectoryListedEvent;
import info.acidflow.waveplay.exceptions.file.FileNotOnExternalStorageException;
import info.acidflow.waveplay.helpers.AudioPlaybackHelper;
import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.util.FileUtils;

/**
 * Created by paul on 15/11/14.
 */
public class LocalFileSystemExplorerInteractor extends AbstractFileSystemExplorerInteractor {

    public LocalFileSystemExplorerInteractor(EventBus localBus) {
        super( localBus );
    }

    @Override
    public void listFilesFromDirectory(String directoryPath) {
        if( directoryPath == null ){
            directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        try {
            List< GsonFile > gsonFiles = FileUtils.listDirectory(directoryPath);
            getLocalBus().post(new DirectoryListedEvent(true, gsonFiles));
        }catch ( FileNotOnExternalStorageException e ) {
            e.handleException();
            getLocalBus().post(new DirectoryListedEvent(false, null));
        }
    }

    @Override
    public void openFile(String filePath) {
       AudioPlaybackHelper.openFile(filePath);
    }
}
