package info.acidflow.waveplay.server.reponses;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.exceptions.server.ResponseBuilderException;

/**
 * Created by paul on 13/10/14.
 */
public abstract class AbstractWavePlayResponse {

    abstract public NanoHTTPD.Response buildResponse() throws ResponseBuilderException;
}
