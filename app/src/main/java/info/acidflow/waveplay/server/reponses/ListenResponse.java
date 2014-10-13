package info.acidflow.waveplay.server.reponses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.server.contants.HttpHeaders;

/**
 * Created by paul on 13/10/14.
 */
public class ListenResponse extends AbstractWavePlayResponse {

    final public static String URI_PATH = "/listen";

    @Override
    public NanoHTTPD.Response buildResponse() {
        File music = new File( "OGG" /* Audio file */ );
        try {
            FileInputStream is = new FileInputStream( music );
            NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK,
                    HttpHeaders.ContentType.CONTENT_TYPE_AUDIO_MPEG , is );
            return response;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
