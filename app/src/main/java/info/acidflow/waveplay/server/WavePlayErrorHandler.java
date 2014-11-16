package info.acidflow.waveplay.server;

import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.bus.events.server.error.HttpErrorEvent;
import info.acidflow.waveplay.bus.events.server.error.NetworkErrorEvent;
import retrofit.RetrofitError;
import timber.log.Timber;

/**
 * Created by paul on 16/11/14.
 */
public class WavePlayErrorHandler {

    private EventBus mLocalBus;

    public WavePlayErrorHandler( EventBus localBus ) {
        super();
        mLocalBus = localBus;
    }

    public void handleError( RetrofitError error ){
        switch ( error.getKind() ){

            case NETWORK:
                handleNetworkError( error );
                break;

            case HTTP:
                handleHttpError( error );
                break;

            case CONVERSION:
                handleConversionError(error);
                break;

            case UNEXPECTED:
                throw error;
        }
    }

    protected void handleNetworkError( RetrofitError error ){
        Timber.e( error, "Network error occured" );
        mLocalBus.post( new NetworkErrorEvent( error ) );
    }

    protected void handleHttpError( RetrofitError error ){
        Timber.e(error, "HTTP error occured on URL : %s", error.getUrl());
        mLocalBus.post( new HttpErrorEvent( error ) );
    }

    protected void handleConversionError( RetrofitError error ){
        Timber.e( error, "Conversion error occured on URL : %s", error.getUrl() );
    }
}
