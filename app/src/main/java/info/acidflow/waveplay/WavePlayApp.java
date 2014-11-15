package info.acidflow.waveplay;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.dagger.modules.AppModule;
import io.fabric.sdk.android.Fabric;

/**
 * Created by paul on 25/10/14.
 */
public class WavePlayApp extends Application {

    private ObjectGraph mObjectGraph;
    private EventBus mDefaultEventBus;

    @Override
    public void onCreate() {
        super.onCreate();
        if( !BuildConfig.DEBUG ) {
            Fabric.with(this, new Crashlytics());
        }
        mObjectGraph = ObjectGraph.create( getModules().toArray() );
        mObjectGraph.inject( this );
    }

    private List<Object> getModules(){
        return Arrays.<Object>asList( new AppModule( this ) );
    }
}
