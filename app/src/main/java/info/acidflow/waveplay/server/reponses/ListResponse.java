package info.acidflow.waveplay.server.reponses;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;
import info.acidflow.waveplay.server.contants.HttpHeaders;
import info.acidflow.waveplay.server.model.GsonFile;

/**
 * Created by paul on 13/10/14.
 */
public class ListResponse extends AbstractWavePlayResponse {

    final public static String URI_PATH = "/list";

    private List<GsonFile> listMusicDir(){
        List< GsonFile > gsonFiles = new ArrayList<GsonFile>();
        File sdcard = Environment.getExternalStorageDirectory();
        File musicDir = new File( "" /* Directory */ );
        if( musicDir.exists() && musicDir.isDirectory() ){
           File[] files = musicDir.listFiles();
            for( File f : files ){
                gsonFiles.add( new GsonFile( f.getName(), f.isDirectory() ) );
            }
        }
        return gsonFiles;
    }

    @Override
    public NanoHTTPD.Response buildResponse() {
        Gson gson = new Gson();
        NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK,
                HttpHeaders.ContentType.CONTENT_TYPE_APPLICATION_JSON,
                gson.toJson( listMusicDir() ) );
        return response;
    }
}
