package info.acidflow.waveplay.bus.events.server.error;

import retrofit.RetrofitError;

/**
 * Created by paul on 16/11/14.
 */
public class NetworkErrorEvent {

    public RetrofitError error;


    public NetworkErrorEvent(RetrofitError error) {
        this.error = error;
    }
}
