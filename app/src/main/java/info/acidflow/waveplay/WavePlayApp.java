package info.acidflow.waveplay;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import de.greenrobot.event.EventBus;
import info.acidflow.waveplay.dagger.modules.AppModule;
import info.acidflow.waveplay.timber.loggers.CrashReportingTree;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by paul on 25/10/14.
 */
public class WavePlayApp extends Application {

    private ObjectGraph mObjectGraph;
    private static EventBus sServerServiceBus;
    private static EventBus sPlayerBus;

    @Override
    public void onCreate() {
        super.onCreate();
        if( !BuildConfig.DEBUG ) {
            Fabric.with(this, new Crashlytics());
            Timber.plant( new CrashReportingTree() );
        }else{
            Timber.plant( new Timber.DebugTree() );
        }
        sServerServiceBus = new EventBus();
        sPlayerBus = new EventBus();
        mObjectGraph = ObjectGraph.create( getModules().toArray() );
        mObjectGraph.inject( this );
    }

    private List<Object> getModules(){
        return Arrays.<Object>asList( new AppModule( this ) );
    }

    public static EventBus getServerServiceBus(){
        return sServerServiceBus;
    }

    public static EventBus getPlayerBus(){
        return sPlayerBus;
    }

}
