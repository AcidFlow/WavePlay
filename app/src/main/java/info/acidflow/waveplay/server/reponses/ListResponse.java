package info.acidflow.waveplay.server.reponses;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.server.contants.HttpHeaders;
import info.acidflow.waveplay.server.model.GsonFile;

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

    private List< GsonFile > listDirectory( String directoryPath ){
        List< GsonFile > gsonFiles = new ArrayList<GsonFile>();
        File folder = new File( directoryPath );
        if( folder.exists() && folder.canRead() && folder.isDirectory()  ){
            File[] files = folder.listFiles();
            for( File f : files ){
                if( f.canRead() ) {
                    gsonFiles.add( new GsonFile( f ) );
                }
            }
        }
        return gsonFiles;
    }



    @Override
    public NanoHTTPD.Response buildResponse() {
        Gson gson = new Gson();
        String folder = mParams.get(PARAM_FOLDER);
        if( folder == null ){
            folder = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK,
                HttpHeaders.ContentType.CONTENT_TYPE_APPLICATION_JSON,
                gson.toJson( listDirectory( folder ) ) );
        return response;
    }
}
