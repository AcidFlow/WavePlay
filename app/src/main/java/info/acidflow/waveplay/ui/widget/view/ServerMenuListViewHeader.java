package info.acidflow.waveplay.ui.widget.view;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import info.acidflow.waveplay.R;
import info.acidflow.waveplay.ui.controllers.server.ServerController;
import info.acidflow.waveplay.ui.views.interfaces.menu.server.ServerView;
import info.acidflow.waveplay.util.NetworkUtils;

/**
 * Created by paul on 15/11/14.
 */
public class ServerMenuListViewHeader extends FrameLayout implements ServerView {

    private ServerController mController;

    @InjectView( R.id.menu_header_icon_server )
    ImageView serverIcon;

    @InjectView( R.id.menu_header_server_status )
    TextView serverStatus;

    @InjectView( R.id.menu_header_server_ip )
    TextView serverIp;

    @InjectView( R.id.menu_header_server_port )
    TextView serverPort;

    @InjectView( R.id.menu_header_server_status_switch )
    SwitchCompat serverSwitch;

    public ServerMenuListViewHeader(Context context ) {
        super(context);
        inflateView();
        mController = new ServerController( this );
    }

    public ServerMenuListViewHeader(Context context, AttributeSet attrs, ServerController controller ) {
        super(context, attrs);
        inflateView();
        mController = new ServerController( this );
    }

    public ServerMenuListViewHeader(Context context, AttributeSet attrs, int defStyleAttr, ServerController controller ) {
        super(context, attrs, defStyleAttr);
        inflateView();
        mController = new ServerController( this );
    }

    private void inflateView(){
        View layout = LayoutInflater.from( getContext() ).inflate( R.layout.list_item_menu_start_server, this );
        ButterKnife.inject( this, layout );
    }


    @OnCheckedChanged( R.id.menu_header_server_status_switch )
    public void startStopServer( boolean checked ){
        if( checked ) {
            mController.startServer( getContext() );
        }else{
            mController.stopServer(getContext());
        }
    }


    @Override
    public void serverStarted() {
        serverSwitch.setChecked( true );
        serverIp.setText(NetworkUtils.getIpAddress(getContext()));
        serverPort.setText("8080");
        serverStatus.setText( "Server online" );
        serverIp.setVisibility( View.VISIBLE );
        serverPort.setVisibility( View.VISIBLE );
    }

    @Override
    public void serverStopped() {
        serverSwitch.setChecked( false );
        serverIp.setVisibility( View.INVISIBLE );
        serverPort.setVisibility( View.INVISIBLE );
        serverStatus.setText("Server offline");
    }

    @Override
    public void refreshView( boolean isServerUp ) {
        if( isServerUp ){
            serverStarted();
        }else{
            serverStopped();
        }
    }
}
