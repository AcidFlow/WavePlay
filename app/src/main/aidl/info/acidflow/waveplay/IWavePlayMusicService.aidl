// IWavePlayMusicService.aidl
package info.acidflow.waveplay;

// Declare any non-default types here with import statements

interface IWavePlayMusicService {

    void openFile( String path );
    void stop();
    void play();
    void pause();
}
