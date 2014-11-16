package info.acidflow.waveplay.ui.controllers;

import android.app.Activity;
import android.content.Context;

import info.acidflow.waveplay.R;
import info.acidflow.waveplay.server.WavePlayServer;
import info.acidflow.waveplay.ui.fragments.FileExplorerFragment;
import info.acidflow.waveplay.ui.views.interfaces.ChooseServerView;
import info.acidflow.waveplay.util.NetworkUtils;

/**
 * Created by paul on 16/11/14.
 */
public class ChooseServerController {

    private ChooseServerView mView;
    private String mIPDest;
    private String mPortDest;

    public ChooseServerController(ChooseServerView view) {
        super();
        mView = view;
    }

    public boolean isUserInputValid(String inputServerIp, String inputServerPort ){
        String ipAddr = inputServerIp.trim();
        String port = inputServerPort.trim();
        if( !NetworkUtils.isValidIPAddress(ipAddr) ){
            mView.errorIpInvalid();
            return false;
        }else{
            mIPDest = ipAddr;
        }
        if( port.isEmpty() ){
            mPortDest = String.valueOf( WavePlayServer.DEFAULT_PORT );
        }else{
            mPortDest = port;
        }
        return true;
    }

    public void connectToServer( Activity activity ){
        mView.disableConnectButton();
        activity.getFragmentManager().beginTransaction()
                .replace(R.id.container,
                        FileExplorerFragment.newInstance( true, mIPDest, mPortDest ) )
                .commit();
    }


}
