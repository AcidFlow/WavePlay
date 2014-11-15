package info.acidflow.waveplay.bus.events.fileexplorer;

import java.util.List;

import info.acidflow.waveplay.server.model.GsonFile;

/**
 * Created by paul on 15/11/14.
 */
public class DirectoryListedEvent {

    public boolean isSuccess;
    public List< GsonFile > files;

    public DirectoryListedEvent(boolean isSuccess, List<GsonFile> files) {
        this.isSuccess = isSuccess;
        this.files = files;
    }

}
