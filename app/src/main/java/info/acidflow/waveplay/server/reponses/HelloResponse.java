package info.acidflow.waveplay.server.reponses;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by paul on 13/10/14.
 */
public class HelloResponse extends AbstractWavePlayResponse {

    final public static String URI_PATH = "/hello";

    @Override
    public NanoHTTPD.Response buildResponse() {
        return new NanoHTTPD.Response( "Hello !" );
    }
}
