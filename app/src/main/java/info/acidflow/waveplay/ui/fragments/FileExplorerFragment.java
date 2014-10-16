package info.acidflow.waveplay.ui.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import info.acidflow.waveplay.R;
import info.acidflow.waveplay.loaders.FilesLoader;
import info.acidflow.waveplay.server.model.GsonFile;

/**
 * Created by paul on 16/10/14.
 */
public class FileExplorerFragment extends Fragment
        implements LoaderManager.LoaderCallbacks< List< GsonFile > >,
        AdapterView.OnItemClickListener {

    final public static String LOG_TAG = FileExplorerFragment.class.getSimpleName();

    final private static String FILE_LOADER_ROOT_DIR_ARG = "file_loader_root_dir_arg";

    @InjectView( R.id.file_explorer_list_view )
    protected ListView mFilesListView;

    public static FileExplorerFragment newInstance(){
        return new FileExplorerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // TODO Instantiate adapters
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader( FilesLoader.LOADER_ID, null, this );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset( this );
    }

    @Override
    public Loader<List<GsonFile>> onCreateLoader(int i, Bundle bundle) {
        String rootDir = null;
        if( bundle != null ) {
            rootDir = bundle.getString( FILE_LOADER_ROOT_DIR_ARG );
        }
        return new FilesLoader( getActivity(), rootDir );
    }

    @Override
    public void onLoadFinished(Loader<List<GsonFile>> listLoader, List<GsonFile> gsonFiles) {
        Log.i(LOG_TAG, "Loading done" );
        if( gsonFiles == null ){
            Log.e(LOG_TAG, "Error occured during listing directory" );
        }else{
            Log.i(LOG_TAG, "List size : " + gsonFiles.size() );
            // TODO Set new data to the adapter
        }
    }

    @Override
    public void onLoaderReset(Loader<List<GsonFile>> listLoader) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO restart the loader with the new root directory if it's a directory or play the file
    }
}
