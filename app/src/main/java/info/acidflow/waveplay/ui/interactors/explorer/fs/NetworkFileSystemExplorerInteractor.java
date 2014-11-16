package info.acidflow.waveplay.ui.interactors.explorer.fs;

import android.net.Uri;
import android.util.Log;

import java.util.List;

import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.bus.events.fileexplorer.DirectoryListedEvent;
import info.acidflow.waveplay.helpers.AudioPlaybackHelper;
import info.acidflow.waveplay.server.WavePlayErrorHandler;
import info.acidflow.waveplay.server.api.WavePlayServerAPI;
import info.acidflow.waveplay.server.contants.uri.APIUri;
import info.acidflow.waveplay.server.model.GsonFile;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * Created by paul on 15/11/14.
 */
public class NetworkFileSystemExplorerInteractor extends AbstractFileSystemExplorerInteractor {

    private WavePlayServerAPI mWavePlayAPI;
    private String mServerIP;
    private String mServerPort;
    private WavePlayErrorHandler mErrorHandler;

    public NetworkFileSystemExplorerInteractor(String ip, String port, EventBus localBus ) {
        super( localBus );
        mServerIP = ip;
        mServerPort = port;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( APIUri.getBaseUri( mServerIP, mServerPort ).toString() )
                .build();
        mWavePlayAPI = restAdapter.create( WavePlayServerAPI.class );
        mErrorHandler = new WavePlayErrorHandler( localBus );
    }

    @Override
    public void listFilesFromDirectory(String directoryPath) {
        try{
            List<GsonFile> gsonFiles = mWavePlayAPI.list( directoryPath );
            getLocalBus().post(new DirectoryListedEvent(true, gsonFiles));
        }catch ( RetrofitError error ){
           mErrorHandler.handleError( error );
        }
    }

    @Override
    public void openFile(String filePath) {
        AudioPlaybackHelper.openFile( APIUri.getListenUri(mServerIP, mServerPort, filePath).toString() );
    }
}
