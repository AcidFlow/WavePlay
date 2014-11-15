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
            mMediaPlayer.start();
        }catch ( IOException e){
            Log.e(getClass().getSimpleName(), "Exception", e );
        }
    }

    public void stop(){
        mMediaPlayer.stop();
        mMediaPlayer.reset();
    }

    public void pause(){
        mMediaPlayer.pause();
    }

    public void play(){
        mMediaPlayer.start();
    }

    public void playOrPause(){
        if( mMediaPlayer.isPlaying() ){
            mMediaPlayer.pause();
        }else{
            mMediaPlayer.start();
        }
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


    }

}
