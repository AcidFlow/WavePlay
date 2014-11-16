package info.acidflow.waveplay.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.R;
import info.acidflow.waveplay.listeners.OnBackPressedListener;
import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.ui.activities.HomeActivity;
import info.acidflow.waveplay.ui.adapters.FileExplorerAdapter;
import info.acidflow.waveplay.ui.controllers.explorer.fs.AbstractFileExplorerController;
import info.acidflow.waveplay.ui.controllers.explorer.fs.LocalFileSystemExplorerController;
import info.acidflow.waveplay.ui.controllers.explorer.fs.NetworkFileSystemExplorerController;
import info.acidflow.waveplay.ui.views.interfaces.explorer.fs.FileExplorerView;

/**
 * Created by paul on 16/10/14.
 */
public class FileExplorerFragment extends Fragment implements
        AdapterView.OnItemClickListener, FileExplorerView, OnBackPressedListener {

    final public static String LOG_TAG = FileExplorerFragment.class.getSimpleName();
    final public static String SAVED_STATE_CURRENT_DIRECTORY = "saved_state_current_directory";
    final public static String ARGS_FROM_NETWORK = "args_from_network";
    final public static String ARGS_SERVER_IP = "args_server_ip";
    final public static String ARGS_SERVER_PORT = "args_server_port";

    @InjectView( R.id.file_explorer_list_view )
    protected ListView mFilesListView;

    @InjectView( R.id.loading )
    protected ProgressBar mLoading;

    private AbstractFileExplorerController mController;
    private EventBus mLocalBus;
    private ArrayAdapter< GsonFile > mAdapter;
    private String mCurrentDirectory = null;

    public FileExplorerFragment() {
        super();
        mLocalBus = new EventBus();
    }

    public static FileExplorerFragment newInstance(){
        return newInstance( false, null, null );
    }

    public static FileExplorerFragment newInstance(boolean fromNetwork, String ip, String port ){
        FileExplorerFragment f = new FileExplorerFragment();
        Bundle args = new Bundle();
        args.putBoolean( ARGS_FROM_NETWORK, fromNetwork );
        args.putString(ARGS_SERVER_IP, ip);
        args.putString(ARGS_SERVER_PORT, port);
        f.setArguments( args );
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArguments();
        if( savedInstanceState != null ){
            mCurrentDirectory = savedInstanceState.getString( SAVED_STATE_CURRENT_DIRECTORY, null );
        }
    }

    private void parseArguments(){
        if( getArguments() != null ){
            boolean fromNetwork = getArguments().getBoolean( ARGS_FROM_NETWORK, false );
            if( fromNetwork ){
                mController = new NetworkFileSystemExplorerController( this, mLocalBus,
                        getArguments().getString( ARGS_SERVER_IP ) ,
                        getArguments().getString( ARGS_SERVER_PORT )
                );
            }else{
                mController = new LocalFileSystemExplorerController( this, mLocalBus );
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new FileExplorerAdapter( getActivity(), R.layout.list_item_file_explorer, new ArrayList<GsonFile>() );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.fragment_file_explorer, container, false );
        ButterKnife.inject( this, v );
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFilesListView.setAdapter( mAdapter );
        mFilesListView.setOnItemClickListener( this );
    }

    @Override
    public void onStart() {
        super.onStart();
        mController.registerToLocalBus();
        mController.getFilesFromDirectory( mCurrentDirectory );
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurrentDirectory = mController.getCurrentRootDirectory();
        mController.unregisterFromLocalBus();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_STATE_CURRENT_DIRECTORY, mCurrentDirectory);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset( this );
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        GsonFile file = mAdapter.getItem(i);
        if( file.isDirectory() ) {
            mController.getFilesFromDirectory( file.getFilePath() );
        }else{
            mController.openFile( file.getFilePath() );
        }
    }

    @Override
    public void showProgress() {
        if( isAdded() ) {
            mLoading.setVisibility(View.VISIBLE);
            mFilesListView.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideProgress() {
        if( isAdded() ) {
            mLoading.setVisibility(View.GONE);
            mFilesListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showData(List<GsonFile> files) {
        if( isAdded() ) {
            mAdapter = new FileExplorerAdapter(getActivity(), R.layout.list_item_file_explorer, files);
            mFilesListView.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onBackPressedCaught() {
        return mController.onBackPressed();
    }


    @Override
    public void showServerUnreachableError() {
        Toast.makeText( getActivity(), getString( R.string.error_connection_refused ), Toast.LENGTH_LONG )
                .show();
        getFragmentManager().beginTransaction()
                .replace( R.id.container, ChooseServerFragment.newInstance() )
                .commit();
    }

    @Override
    public void showNetworkError() {
        Toast.makeText( getActivity(), getString( R.string.error_network_default ), Toast.LENGTH_LONG )
                .show();
        getFragmentManager().beginTransaction()
                .replace( R.id.container, ChooseServerFragment.newInstance() )
                .commit();
    }

    @Override
    public void showHttpError() {
        Toast.makeText( getActivity(), getString( R.string.error_http_default ), Toast.LENGTH_LONG )
                .show();
    }

    // TODO Redo code below this point. It was for testing purpose
    @Override
    public void showCurrentlyPlaying() {
        ((HomeActivity) getActivity()).showCurrentlyPlaying();
    }

}
