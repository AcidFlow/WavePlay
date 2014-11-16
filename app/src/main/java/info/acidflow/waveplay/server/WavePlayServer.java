package info.acidflow.waveplay.server;

import android.util.Log;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.server.reponses.WavePlayServerResponseFactory;

/**
 * Created by AcidFlow on 12/10/2014.
 */
public class WavePlayServer extends NanoHTTPD {

    final static String LOG_TAG = WavePlayServer.class.getSimpleName();
    final public static int DEFAULT_PORT = 8080;
    private boolean isServerStarted;

    public WavePlayServer() {
        super( DEFAULT_PORT );
        isServerStarted = false;
    }


    @Override
    public Response serve(IHTTPSession session) {
        return WavePlayServerResponseFactory.generateResponse( session );
    }

    public boolean startServer(){
        if( !isServerStarted ) {
            try {
                start();
                isServerStarted = true;
            } catch ( IOException e ) {
                Log.e( LOG_TAG, e.toString() );
                isServerStarted = false;
            }
        }else{
            Log.i( LOG_TAG, "Server is already running" );
        }
        return isServerStarted;
    }

    public void stopServer(){
        if( isServerStarted ) {
            closeAllConnections();
            stop();
        }else{
            Log.i(LOG_TAG, "Server already stopped or not started" );
        }
    }


}
