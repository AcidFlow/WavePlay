package info.acidflow.waveplay.server.reponses;

import android.os.Environment;

import com.google.gson.Gson;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.exceptions.file.FileNotOnExternalStorageException;
import info.acidflow.waveplay.exceptions.server.ResponseBuilderException;
import info.acidflow.waveplay.server.contants.HttpHeaders;
import info.acidflow.waveplay.util.FileUtils;

/**
 * Created by paul on 13/10/14.
 */
public class ListResponse extends AbstractWavePlayResponse {

    final public static String URI_PATH = "/list";
    final public static String PARAM_FOLDER = "folder";
    private Map< String, String > mParams;

    public ListResponse( Map<String, String> params ){
        super();
        mParams = params;
    }


    @Override
    public NanoHTTPD.Response buildResponse() throws ResponseBuilderException {
        Gson gson = new Gson();
        String folder = mParams.get(PARAM_FOLDER);
        if( folder == null ){
            folder = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        try {
            NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK,
                    HttpHeaders.ContentType.CONTENT_TYPE_APPLICATION_JSON,
                    gson.toJson(FileUtils.listDirectory(folder)));
            return response;
        }catch ( FileNotOnExternalStorageException e ){
            throw new ResponseBuilderException( e );
        }
    }
}
