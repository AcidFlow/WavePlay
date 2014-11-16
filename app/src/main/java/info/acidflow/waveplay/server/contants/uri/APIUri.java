package info.acidflow.waveplay.server.contants.uri;

import android.net.Uri;

import info.acidflow.waveplay.server.reponses.ListenResponse;

/**
 * Created by paul on 16/11/14.
 */
public class APIUri {

    final private static String API_SCHEME = "http";

    private APIUri(){

    }

    public static Uri getBaseUri( String ip, String port ){
        StringBuilder authority = new StringBuilder( ip ).append( ":" ).append( port );
        Uri uri = new Uri.Builder()
                .scheme( API_SCHEME )
                .encodedAuthority( authority.toString() )
                .build();
        return uri;
    }

    public static Uri getListenUri( String ip, String port, String file ){
        return getBaseUri( ip, port ).buildUpon()
                .appendPath( ListenResponse.URI_PATH.substring( 1 ) )
                .appendQueryParameter( ListenResponse.PARAM_FILE, file )
                .build();
    }


}
