package info.acidflow.waveplay;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import info.acidflow.waveplay.dagger.modules.AppModule;

/**
 * Created by paul on 25/10/14.
 */
public class WavePlayApp extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create( getModules().toArray() );
        mObjectGraph.inject( this );
    }

    private List<Object> getModules(){
        return Arrays.<Object>asList( new AppModule( this ) );
    }
}
