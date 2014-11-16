package info.acidflow.waveplay.exceptions.server;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by paul on 16/10/14.
 */
public class AbstractWavePlayServerException extends Exception {

    final public static String LOG_TAG = AbstractWavePlayServerException.class.getSimpleName();

    public AbstractWavePlayServerException() {
        super();
    }

    public AbstractWavePlayServerException(String detailMessage) {
        super(detailMessage);
    }

    public AbstractWavePlayServerException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AbstractWavePlayServerException(Throwable throwable) {
        super(throwable);
    }

    public void handleException(){
        Timber.e( this, "Exception raised by WavePlayServer" );
    }
}
