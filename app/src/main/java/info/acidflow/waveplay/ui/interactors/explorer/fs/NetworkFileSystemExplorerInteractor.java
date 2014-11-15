package info.acidflow.waveplay.ui.interactors.explorer.fs;

import android.net.Uri;

import java.util.List;

import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.bus.events.fileexplorer.DirectoryListedEvent;
import info.acidflow.waveplay.helpers.AudioPlaybackHelper;
import info.acidflow.waveplay.server.api.WavePlayServerAPI;
import info.acidflow.waveplay.server.model.GsonFile;
import retrofit.RestAdapter;

/**
 * Created by paul on 15/11/14.
 */
public class NetworkFileSystemExplorerInteractor extends AbstractFileSystemExplorerInteractor {

    private WavePlayServerAPI mWavePlayAPI;

    public NetworkFileSystemExplorerInteractor(String ip, String port, EventBus localBus ) {
        super( localBus );
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint( "http://" + ip + ":" + port )
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
        AudioPlaybackHelper.openFile("http://192.168.0.14:8080/listen?file=" + Uri.encode(filePath));
    }
}
