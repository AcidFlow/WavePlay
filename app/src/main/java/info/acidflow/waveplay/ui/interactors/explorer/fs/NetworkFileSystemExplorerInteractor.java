package info.acidflow.waveplay.ui.interactors.explorer.fs;

import android.net.Uri;
import android.util.Log;

import java.util.List;

import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.bus.events.fileexplorer.DirectoryListedEvent;
import info.acidflow.waveplay.helpers.AudioPlaybackHelper;
import info.acidflow.waveplay.server.api.WavePlayServerAPI;
import info.acidflow.waveplay.server.contants.uri.APIUri;
import info.acidflow.waveplay.server.model.GsonFile;
import retrofit.RestAdapter;

/**
 * Created by paul on 15/11/14.
 */
public class NetworkFileSystemExplorerInteractor extends AbstractFileSystemExplorerInteractor {

    private WavePlayServerAPI mWavePlayAPI;
    private String mServerIP;
    private String mServerPort;

    public NetworkFileSystemExplorerInteractor(String ip, String port, EventBus localBus ) {
        super( localBus );
        mServerIP = ip;
        mServerPort = port;
        Log.i("PLop", APIUri.getBaseUri( mServerIP, mServerPort ).toString() );
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( APIUri.getBaseUri( mServerIP, mServerPort ).toString() )
                .build();
        mWavePlayAPI = restAdapter.create( WavePlayServerAPI.class );
    }

    @Override
    public void listFilesFromDirectory(String directoryPath) {
        List< GsonFile > gsonFiles = mWavePlayAPI.list( directoryPath );
        getLocalBus().post(new DirectoryListedEvent(true, gsonFiles));
    }

    @Override
    public void openFile(String filePath) {
        Log.i("Plop2", APIUri.getListenUri(mServerIP, mServerPort, filePath).toString() );
        AudioPlaybackHelper.openFile( APIUri.getListenUri(mServerIP, mServerPort, filePath).toString() );
    }
}
