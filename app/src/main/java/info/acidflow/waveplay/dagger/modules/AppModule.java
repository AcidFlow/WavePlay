package info.acidflow.waveplay.dagger.modules;

import dagger.Module;
import info.acidflow.waveplay.WavePlayApp;

/**
 * Created by paul on 25/10/14.
 */
@Module(
        injects = {
                WavePlayApp.class
        }
)
public class AppModule {

    private WavePlayApp mApplication;

    public AppModule( WavePlayApp app ){
        mApplication = app;
    }

//    @Provides @Singleton public Context provideApplicationContext(){
//        return mApplication;
//    }

}
