package info.acidflow.waveplay.ui.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import info.acidflow.waveplay.R;
import info.acidflow.waveplay.helpers.AudioPlaybackHelper;
import info.acidflow.waveplay.listeners.OnBackPressedListener;
import info.acidflow.waveplay.service.WavePlayServerService;
import info.acidflow.waveplay.ui.controllers.ChooseServerController;
import info.acidflow.waveplay.ui.fragments.ChooseServerFragment;
import info.acidflow.waveplay.ui.fragments.FileExplorerFragment;
import info.acidflow.waveplay.ui.fragments.NavigationDrawerFragment;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @InjectView( R.id.home_activity_toolbar )
    Toolbar mToolbar;

    @InjectView( R.id.currently_playing_layout )
    View mCurrentlyPlayingView;

    @InjectView( R.id.currently_playing_title )
    TextView mCurrentlyPlayingTitle;

    private AudioPlaybackHelper.AudioPlaybackServiceToken mAudioPlaybackServiceToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject( this );
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                mToolbar );
        if( savedInstanceState == null ) {
            hideCurrentlyPlaying();
        }
        mAudioPlaybackServiceToken = AudioPlaybackHelper.bindToService( this, null );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlaybackHelper.unbindFromService( mAudioPlaybackServiceToken );
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch ( position ){
            case 0:
                fragment = FileExplorerFragment.newInstance();
                break;
            case 1:
                fragment = ChooseServerFragment.newInstance();
                break;
            default:
                fragment = FileExplorerFragment.newInstance( true, "192.168.0.14", "8080" );
                break;
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace( R.id.container, fragment )
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public void onBackPressed() {
        Fragment current = getFragmentManager().findFragmentById( R.id.container );
        if( !( current != null && ( current instanceof OnBackPressedListener )
                && ( ( OnBackPressedListener ) current).onBackPressedCaught() ) ){
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    // TODO Redo code below this point. It was for testing purpose
    public void showCurrentlyPlaying(){
        mCurrentlyPlayingView.setVisibility( View.VISIBLE );
    }

    public void hideCurrentlyPlaying(){
        mCurrentlyPlayingView.setVisibility( View.GONE );
    }

    public void setCurrentlyPlayingTitle( String str ){
        mCurrentlyPlayingTitle.setText( str );
    }

    @OnClick( R.id.currently_playing_pause )
    public void pauseCurrentPlaying(){
        AudioPlaybackHelper.playOrPause();
    }
}
