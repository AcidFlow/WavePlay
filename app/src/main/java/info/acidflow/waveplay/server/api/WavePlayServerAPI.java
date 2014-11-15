package info.acidflow.waveplay.server.api;

import java.util.List;

import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.server.reponses.ListResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by paul on 15/11/14.
 */
public interface WavePlayServerAPI {

    @GET( ListResponse.URI_PATH )
    List< GsonFile > list( @Query( ListResponse.PARAM_FOLDER ) String rootDir );

}
