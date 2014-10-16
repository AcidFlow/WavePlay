package info.acidflow.waveplay.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

import info.acidflow.waveplay.server.WavePlayServer;

/**
 * Created by paul on 13/10/14.
 */
public class WavePlayServerService extends Service {

    private WavePlayServer mServer;
    private MediaPlayer mMediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServer = new WavePlayServer();
        mServer.startServer();
        mMediaPlayer= new MediaPlayer();
        try {
            mMediaPlayer.setDataSource( "http://localhost:8080/listen?file=/storage/emulated/0/Music/Qlimax/2011/01-zatox-no_way_back_(qlimax_anthem_2011).flac" );
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }catch (IOException e ){
            Log.e("PAUL", "IO", e);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mServer.stopServer();
    }
}
