package info.acidflow.waveplay.server.reponses;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by paul on 13/10/14.
 */
public class NotFoundResponse extends AbstractWavePlayResponse {

    @Override
    public NanoHTTPD.Response buildResponse() {
        return new NanoHTTPD.Response( NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Not found !");
    }
}
