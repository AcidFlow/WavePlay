package info.acidflow.waveplay.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by paul on 15/11/14.
 */
public class NetworkUtils {

    private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static String getIpAddress( Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService( Context.WIFI_SERVICE );
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    public static String intToIp(int i) {
        return  ( i & 0xFF) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ((i >> 24 ) & 0xFF );
    }

    public static boolean isValidIPAddress( String ip ){
        if( ip == null ){
            return false;
        }
        return ip.matches( IPADDRESS_PATTERN );

    }
}
