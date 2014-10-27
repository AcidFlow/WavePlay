package info.acidflow.waveplay.helpers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.WeakHashMap;

import info.acidflow.waveplay.IWavePlayMusicService;
import info.acidflow.waveplay.service.WavePlayAudioPlaybackService;

/**
 * Created by paul on 27/10/14.
 */
public class AudioPlaybackHelper {

    public static IWavePlayMusicService mService = null;
    private static final WeakHashMap<Context, AudioPlaybackServiceBinder> mConnectionMap;

    static {
        mConnectionMap = new WeakHashMap<Context, AudioPlaybackServiceBinder >();
    }

    private AudioPlaybackHelper(){};


    public static AudioPlaybackServiceToken bindToService( final Context context, final ServiceConnection callback ){
        Activity realActivity = ( ( Activity ) context ).getParent();
        if (realActivity == null) {
            realActivity = ( Activity ) context;
        }
        final ContextWrapper contextWrapper = new ContextWrapper( realActivity );
        Intent service = new Intent( contextWrapper , WavePlayAudioPlaybackService.class );
        context.startService( service );
        AudioPlaybackServiceBinder binder = new AudioPlaybackServiceBinder( callback );
        if( context.bindService( service, binder, 0 ) ){
            mConnectionMap.put( contextWrapper, binder );
            return new AudioPlaybackServiceToken( contextWrapper );
        }
        return null;
    }

    public static void unbindFromService( final AudioPlaybackServiceToken token ){
        if( token == null ){
            return;
        }
        ContextWrapper contextWrapper = token.mWrappedContext;
        AudioPlaybackServiceBinder binder = mConnectionMap.remove( contextWrapper );
        if( binder == null ) {
            return;
        }
        contextWrapper.unbindService( binder );
        if ( mConnectionMap.isEmpty() ) {
            mService = null;
        }
    }


    public static final class AudioPlaybackServiceBinder implements ServiceConnection {
        private final ServiceConnection mCallback;

        public AudioPlaybackServiceBinder(final ServiceConnection callback) {
            mCallback = callback;
        }

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            mService = IWavePlayMusicService.Stub.asInterface( service );
            if ( mCallback != null ) {
                mCallback.onServiceConnected(className, service);
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            if ( mCallback != null ) {
                mCallback.onServiceDisconnected(className);
            }
            mService = null;
        }
    }

    public static final class AudioPlaybackServiceToken {
        public ContextWrapper mWrappedContext;

        /**
         * Constructor of <code>ServiceToken</code>
         *
         * @param context The {@link ContextWrapper} to use
         */
        public AudioPlaybackServiceToken(final ContextWrapper context) {
            mWrappedContext = context;
        }
    }

    public static void openFile( String path ){
        if( mService != null ){
            try {
                mService.openFile( path );
            } catch ( RemoteException ignored ) {

            }
        }
    }

    public static void playOrPause(){
        if( mService != null ){
            try {
                mService.playOrPause();
            } catch ( RemoteException ignored ) {

            }
        }
    }


}
