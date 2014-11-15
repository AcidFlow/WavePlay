package info.acidflow.waveplay.bus.events.server;

/**
 * Created by paul on 15/11/14.
 */
public class ServerStatusEvent {

    public boolean isOnline;

    public ServerStatusEvent(boolean isOnline) {
        this.isOnline = isOnline;
    }
}
