package info.acidflow.waveplay.timber.loggers;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by paul on 16/11/14.
 */
public class CrashReportingTree extends Timber.HollowTree {

    final private static String TAG_ERROR = "ERROR : Exception raised";

    @Override
    public void e(Throwable t, String message, Object... args) {
        Fabric.getLogger().e( TAG_ERROR, String.format( message, args ), t );
    }


}
