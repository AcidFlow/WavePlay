package info.acidflow.waveplay.ui.views.interfaces.explorer.fs;

import java.util.List;

import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.ui.views.interfaces.LoadingView;

/**
 * Created by paul on 25/10/14.
 */
public interface FileExplorerView  extends LoadingView {

    public void showData( List<GsonFile> files );

    public void showCurrentlyPlaying();

}
