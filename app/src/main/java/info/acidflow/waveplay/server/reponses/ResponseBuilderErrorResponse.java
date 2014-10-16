package info.acidflow.waveplay.server.reponses;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.exceptions.server.ResponseBuilderException;

/**
 * Created by paul on 13/10/14.
 */
public class ResponseBuilderErrorResponse extends AbstractWavePlayResponse {

    private ResponseBuilderException mException;

    public ResponseBuilderErrorResponse( ResponseBuilderException e ){
        super();
        mException = e ;
    }

    @Override
    public NanoHTTPD.Response buildResponse() {
        return new NanoHTTPD.Response( NanoHTTPD.Response.Status.INTERNAL_ERROR,
                NanoHTTPD.MIME_PLAINTEXT, mException.getMessage()
        );
    }
}
