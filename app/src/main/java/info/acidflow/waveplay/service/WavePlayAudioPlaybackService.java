package info.acidflow.waveplay.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.lang.ref.WeakReference;

import info.acidflow.waveplay.IWavePlayMusicService;
import info.acidflow.waveplay.WavePlayApp;
import info.acidflow.waveplay.bus.events.player.PauseEvent;
import info.acidflow.waveplay.bus.events.player.StartPlayingEvent;
import timber.log.Timber;

/**
 * Created by paul on 16/10/14.
 */
public class WavePlayAudioPlaybackService extends Service {

    private final IBinder mBinder = new WavePlayAudioPlaybackServiceStub( this );
    private MediaPlayer mMediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

// TODO Redo code below this point. It was for testing purpose
    public void openFile( String path ){
        try {
            if( mMediaPlayer.isPlaying() ){
                stop();
            }
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            Timber.i( "File duration : %d ms", mMediaPlayer.getDuration() );
            play();
        }catch ( IOException e){
            Timber.e( e ,"IOException when opening file %s ", path );
        }
    }

    public void stop(){
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public void pause(){
        mMediaPlayer.pause();
        WavePlayApp.getPlayerBus().post( new PauseEvent() );
    }

    public void play(){
        mMediaPlayer.start();
        WavePlayApp.getPlayerBus().post( new StartPlayingEvent() );
    }

    public void playOrPause(){
        if( mMediaPlayer.isPlaying() ){
            pause();
        }else{
            play();
        }
    }

    public long position(){
        return mMediaPlayer.getCurrentPosition();
    }

    public long duration(){
        return mMediaPlayer.getDuration();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private static final class WavePlayAudioPlaybackServiceStub extends IWavePlayMusicService.Stub {

        private final WeakReference< WavePlayAudioPlaybackService > mServiceWeakRef;

        private WavePlayAudioPlaybackServiceStub( final WavePlayAudioPlaybackService service ) {
            mServiceWeakRef = new WeakReference<WavePlayAudioPlaybackService>( service );
        }

        @Override
        public void openFile(String path) throws RemoteException {
            mServiceWeakRef.get().openFile( path );
        }

        @Override
        public void stop() throws RemoteException {
            mServiceWeakRef.get().stop();
        }

        @Override
        public void pause(){
            mServiceWeakRef.get().playOrPause();
        }

        @Override
        public void play(){
            mServiceWeakRef.get().play();
        }

        @Override
        public void playOrPause(){
            mServiceWeakRef.get().playOrPause();
        }

        @Override
        public long position(){
            return mServiceWeakRef.get().position();
        }

        @Override
        public long duration(){
            return mServiceWeakRef.get().duration();
        }



    }

}
