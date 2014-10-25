package info.acidflow.waveplay.ui.views.interfaces;

import java.util.List;

import info.acidflow.waveplay.server.model.GsonFile;

/**
 * Created by paul on 25/10/14.
 */
public interface FileExplorerView  extends LoadingView {

    public void showData( List<GsonFile> files );

    public void onPresenterReady();

}
