package info.acidflow.waveplay.server.reponses;

import android.net.Uri;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by paul on 13/10/14.
 */
public class WavePlayServerResponseFactory {

    public static AbstractWavePlayResponse generateResponse( NanoHTTPD.IHTTPSession session ){
        Uri uri = Uri.parse(session.getUri());

        if( HelloResponse.URI_PATH.equals(uri.getPath()) ) {
            return new HelloResponse();
        }else if ( ListResponse.URI_PATH.equals( uri.getPath() ) ) {
            return new ListResponse();
        }else if ( ListenResponse.URI_PATH.equals(uri.getPath()) ) {
            return new ListenResponse();
        }else{
            return new NotFoundResponse();
        }
    }
}
