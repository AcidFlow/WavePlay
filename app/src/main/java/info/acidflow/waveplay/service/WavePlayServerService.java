package info.acidflow.waveplay.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import info.acidflow.waveplay.server.WavePlayServer;

/**
 * Created by paul on 13/10/14.
 */
public class WavePlayServerService extends Service {

    private WavePlayServer mServer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServer = new WavePlayServer();
        mServer.startServer();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mServer.stopServer();
    }
}
