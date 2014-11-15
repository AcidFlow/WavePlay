package info.acidflow.waveplay.ui.controllers.server;

import android.content.Context;
import android.content.Intent;

import info.acidflow.waveplay.server.WavePlayServer;
import info.acidflow.waveplay.service.WavePlayServerService;
import info.acidflow.waveplay.ui.views.interfaces.menu.server.ServerView;

/**
 * Created by paul on 15/11/14.
 */
public class ServerController {

    private ServerView mServerView;

    public ServerController( ServerView serverView ){
        super();
        mServerView = serverView;
    }

    public void startServer( Context context ){
        context.startService( new Intent( context, WavePlayServerService.class ) );
        mServerView.serverStarted();

    }

    public void stopServer(Context context ){
        context.stopService(new Intent(context, WavePlayServerService.class));
        mServerView.serverStopped();
    }

}
