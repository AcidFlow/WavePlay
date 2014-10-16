package info.acidflow.waveplay.server.reponses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.exceptions.server.ResponseBuilderException;
import info.acidflow.waveplay.server.contants.HttpHeaders;
import info.acidflow.waveplay.util.FileUtils;

/**
 * Created by paul on 13/10/14.
 */
public class ListenResponse extends AbstractWavePlayResponse {

    final public static String URI_PATH = "/listen";
    final public static String PARAM_FILE = "file";

    private Map< String, String > mParams;

    public ListenResponse( Map<String, String> params  ){
        super();
        mParams = params;
    }

    @Override
    public NanoHTTPD.Response buildResponse() throws ResponseBuilderException {
        File music = new File( mParams.get( PARAM_FILE ) );
        try {
            FileInputStream is = new FileInputStream( music );
            NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK,
                    HttpHeaders.ContentType.CONTENT_TYPE_PREFIX + FileUtils.guessMimeType( music ),
                    is
            );
            return response;
        } catch (FileNotFoundException e) {
            throw new ResponseBuilderException( e );
        }
    }
}
