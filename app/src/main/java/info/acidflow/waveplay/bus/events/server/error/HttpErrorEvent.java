package info.acidflow.waveplay.bus.events.server.error;

import retrofit.RetrofitError;

/**
 * Created by paul on 16/11/14.
 */
public class HttpErrorEvent {

    public RetrofitError error;

    public HttpErrorEvent(RetrofitError error) {
        this.error = error;
    }
}
