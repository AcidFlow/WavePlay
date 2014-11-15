package info.acidflow.waveplay.ui.views.interfaces.menu.server;

/**
 * Created by paul on 15/11/14.
 */
public interface ServerView {

    public void serverStarted();
    public void serverStopped();
    public void refreshView( boolean isServerUp );
}
