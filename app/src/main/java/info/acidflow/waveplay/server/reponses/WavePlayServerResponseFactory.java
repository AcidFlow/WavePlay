package info.acidflow.waveplay.server.reponses;

import android.net.Uri;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.exceptions.server.ResponseBuilderException;

/**
 * Created by paul on 13/10/14.
 */
public class WavePlayServerResponseFactory {

    public static NanoHTTPD.Response generateResponse( NanoHTTPD.IHTTPSession session ){

        Uri uri = Uri.parse( session.getUri() );
        AbstractWavePlayResponse wavePlayResponse = null;
            if (HelloResponse.URI_PATH.equals(uri.getPath())) {
                wavePlayResponse = new HelloResponse();

            } else if ( ListResponse.URI_PATH.equals(uri.getPath())) {
                wavePlayResponse = new ListResponse( session.getParms() );

            } else if ( ListenResponse.URI_PATH.equals( uri.getPath() ) ) {
                wavePlayResponse = new ListenResponse( session.getParms() );

            } else {
                wavePlayResponse = new NotFoundResponse();
            }
        try {
            return wavePlayResponse.buildResponse();
        }catch ( ResponseBuilderException e ){
            e.handleException();
            return new ResponseBuilderErrorResponse( e ).buildResponse();
        }
    }
}
