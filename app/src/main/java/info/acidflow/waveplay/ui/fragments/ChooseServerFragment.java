package info.acidflow.waveplay.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import info.acidflow.waveplay.BuildConfig;
import info.acidflow.waveplay.R;
import info.acidflow.waveplay.server.WavePlayServer;
import info.acidflow.waveplay.ui.controllers.ChooseServerController;
import info.acidflow.waveplay.ui.views.interfaces.ChooseServerView;
import info.acidflow.waveplay.util.NetworkUtils;

/**
 * Created by paul on 16/11/14.
 */
public class ChooseServerFragment extends Fragment implements ChooseServerView{

    @InjectView( R.id.input_server_ip )
    EditText mInputServerIp;

    @InjectView( R.id.input_server_port )
    EditText mInputServerPort;

    @InjectView( R.id.connect_to_server )
    Button mConnectButton;

    private ChooseServerController mController;

    public static ChooseServerFragment newInstance(){
        return new ChooseServerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mController = new ChooseServerController( this );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate( R.layout.fragment_choose_server, container, false );
        ButterKnife.inject( this, v );
        if( BuildConfig.DEBUG ){
            mInputServerIp.setText("192.168.");
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset( this );
    }



    @OnClick( R.id.connect_to_server )
    public void connectToServer(){
        if( mController.isUserInputValid(
                mInputServerIp.getText().toString(), mInputServerPort.getText().toString()) ){
            mController.connectToServer( getActivity() );
        }
    }

    @Override
    public void errorIpInvalid() {
        mInputServerIp.setError( getString( R.string.fragment_choose_server_error_ip_invalid ) );
    }

    @Override
    public void disableConnectButton() {
        mConnectButton.setEnabled( false );
    }

    @Override
    public void enableConnectButton() {
        mConnectButton.setEnabled( true );
    }
}
