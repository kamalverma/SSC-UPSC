package com.corelibrary.common.engine;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Class to check network speed and more
 *
 * @author kamal
 */
public class NetworkUtils {

    public static final String TAG = NetworkUtils.class.getSimpleName();

    private static final int NOTIFICATION_ID = 5001;

    public static final Environment DEFAULT_ENVIRONMENT = Environment.BUILD1;
    private static final String KEY_ENVIRONMENT = "KEY_ENVIRONMENT";

    public enum Environment {

        PROD("https", "www.ixigo.com", "edge.ixigo.com", "images.ixigo.com/node_image"),
        APP1("https", "app1.ixigo.com", "app1.ixigo.com", "www.ixigo.com/node_image"),
        BUILD1("http", "54.85.200.166", "54.85.200.166", "54.85.200.166"),
        BUILD4("https", "build4.ixigo.com", "build4.ixigo.com", "www.ixigo.com/node_image");

        private String scheme;
        private String host;
        private String edgeHost;
        private String imageHost;

        Environment(String scheme, String host, String edgeHost, String imageHost) {
            this.scheme = scheme;
            this.host = host;
            this.edgeHost = edgeHost;
            this.imageHost = imageHost;
        }

        public String getScheme() {
            return scheme;
        }

        public String getHost() {
            return host;
        }

        public String getEdgeHost() {
            return edgeHost;
        }

        public String getImageHost() {
            return imageHost;
        }

        public static Environment parseEnvironment(String environment) {
            for (Environment env : values()) {
                if (env.name().equalsIgnoreCase(environment)) {
                    return env;
                }
            }
            return null;
        }
    }

    private static Environment environment = DEFAULT_ENVIRONMENT;

    public static String getIxigoHost() {
        return environment.getHost();
    }

    public static String getIxigoEdgeHost() {
        return environment.getEdgeHost();
    }

    public static String getIxigoImageHost() {
        return environment.getImageHost();
    }

    public static String getIxigoPrefixHost() {
        return environment.getScheme() + "://" + environment.getHost();
    }

    public static String getIxigoPrefixEdgeHost() {
        return environment.getScheme() + "://" + environment.getEdgeHost();
    }

    public static String getIxigoPrefixImageHost() {
        return environment.getScheme() + "://" + environment.getImageHost();
    }

    public static void setEnvironment(Context context, Environment environment) {
        if (Environment.PROD != environment) {
            NetworkUtils.issueEnvironmentNotification(context, environment);
        } else {
            NetworkUtils.clearEnvironmentNotification(context);
        }
        NetworkUtils.environment = environment;
    }

    public static Environment getEnvironment() {
        return environment;
    }


    /**
     * Get the network info
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = NetworkUtils.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context) {
        NetworkInfo info = NetworkUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = NetworkUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = NetworkUtils.getNetworkInfo(context);
        return (info != null && info.isConnected() && NetworkUtils.isConnectionFast(info.getType(), info.getSubtype()));
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion to appropriate level to use these
				 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static void issueEnvironmentNotification(Context context, Environment environment) {

        Intent intent = new Intent(context.getPackageName() + ".SWITCH_ENVIRONMENT");
        intent.putExtra(KEY_ENVIRONMENT, Environment.PROD);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(context.getResources().getIdentifier("pw_notification", "drawable", context.getPackageName())).setAutoCancel(true)
                .setContentTitle("Using " + environment.getHost()).setContentText("Tap to reset").setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        Log.i(TAG, "Issuing notification for: " + environment.name());
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public static void clearEnvironmentNotification(Context context) {
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.cancel(NOTIFICATION_ID);
    }

    public static class SwitchEnvironmentRequestReceiver extends BroadcastReceiver {

        public static String TAG = SwitchEnvironmentRequestReceiver.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive");
            Environment environment = null;
            if (intent.hasExtra(KEY_ENVIRONMENT)) {
                environment = (Environment) intent.getSerializableExtra(KEY_ENVIRONMENT);
            } else if (intent.hasExtra("ENVIRONMENT")) {
                environment = Environment.parseEnvironment(intent.getStringExtra("ENVIRONMENT"));
            }

            if (environment != null) {
                NetworkUtils.setEnvironment(context, environment);
            }
        }

    }

}
