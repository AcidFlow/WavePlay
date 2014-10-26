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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.acidflow.waveplay.R;
import info.acidflow.waveplay.listeners.OnBackPressedListener;
import info.acidflow.waveplay.server.model.GsonFile;
import info.acidflow.waveplay.ui.activities.HomeActivity;
import info.acidflow.waveplay.ui.adapters.FileExplorerAdapter;
import info.acidflow.waveplay.ui.presenters.impl.FileExplorerPresenterImpl;
import info.acidflow.waveplay.ui.presenters.interfaces.FileExplorerPresenter;
import info.acidflow.waveplay.ui.views.interfaces.FileExplorerView;

/**
 * Created by paul on 16/10/14.
 */
public class FileExplorerFragment extends Fragment implements
        AdapterView.OnItemClickListener, FileExplorerView, OnBackPressedListener {

    final public static String LOG_TAG = FileExplorerFragment.class.getSimpleName();
    final public static String SAVED_STATE_CURRENT_DIRECTORY = "saved_state_current_directory";

    @InjectView( R.id.file_explorer_list_view )
    protected ListView mFilesListView;

    @InjectView( R.id.loading )
    protected ProgressBar mLoading;

    private FileExplorerPresenter mPresenter;
    private ArrayAdapter< GsonFile > mAdapter;
    private String mCurrentDirectory = null;

    public FileExplorerFragment() {
        super();
        mPresenter = FileExplorerPresenterImpl.newInstance( this );
    }

    public static FileExplorerFragment newInstance(){
        FileExplorerFragment f = new FileExplorerFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( savedInstanceState != null ){
            mCurrentDirectory = savedInstanceState.getString(SAVED_STATE_CURRENT_DIRECTORY, null );
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
        if( mPresenter instanceof Fragment ){
            getFragmentManager().beginTransaction()
                    .add( ( Fragment ) mPresenter, String.valueOf(mPresenter.getClass().hashCode()))
                    .commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mCurrentDirectory = mPresenter.getCurrentRootDirectory();
        getFragmentManager().beginTransaction()
                .remove( (Fragment) mPresenter )
                .commitAllowingStateLoss();
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
            mPresenter.getFilesFromDirectory( file.getFilePath() );
        }else{
            mPresenter.openFile( file.getFilePath() );
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
    public void onPresenterReady() {
        mPresenter.getFilesFromDirectory(mCurrentDirectory);
    }

    @Override
    public boolean onBackPressedCaught() {
        return mPresenter.onBackPressed();
    }


    // TODO Redo code below this point. It was for testing purpose
    @Override
    public void showCurrentlyPlaying() {
        ((HomeActivity) getActivity()).showCurrentlyPlaying();
    }
}
