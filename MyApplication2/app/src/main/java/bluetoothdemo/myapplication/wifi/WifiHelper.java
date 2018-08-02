package bluetoothdemo.myapplication.wifi;

import java.util.List;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.AuthAlgorithm;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiHelper {

    public static final int SECURITY_NONE = 0;
    public static final int SECURITY_WEP = 1;
    public static final int SECURITY_PSK = 2; // WPA、WPA2、WPA_WPA2
    public static final int SECURITY_EAP = 3;


    public static void startScan(Context context) {
        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
            Toast.makeText(context, "未打开GPS,无法扫描", Toast.LENGTH_SHORT).show();
        } else {
            //开始扫描
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            manager.startScan();
        }

    }

    public static List<ScanResult> getScanResults(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.getScanResults();
    }

    /**
     * wifi是否打开
     *
     * @param context
     * @return
     */
    public static boolean isWifiOpen(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.isWifiEnabled();
    }

    /**
     * 开启wifi
     *
     * @param context
     * @param isEnabled
     */
    public static void setWifiEnabled(Context context, boolean isEnabled) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(isEnabled);  //是否打开需监听广播 WIFI_STATE_CHANGED_ACTION
    }

    /**
     * 获取链接wifi名  没连接为空
     * @param context
     * @return
     */
    public static String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = manager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
    /**
     * 获取链接wifi IP地址
     * @param context
     * @return
     */

    public static String getWifiIP(Context context) {
        String ip = null;
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            ip = (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                    + "." + (i >> 24 & 0xFF);
        }
        return ip;
    }


    public static int updateWifiConfiguration(Context context, WifiConfiguration configuration) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.updateNetwork(configuration);
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        if (networkInfos == null) {
            return false;
        }
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }

        return false;
    }

    public static WifiConfiguration checkWifiConfiguration(Context context, String ssid) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        List<WifiConfiguration> configurations = manager.getConfiguredNetworks();
        if (configurations == null) {
            return null;
        }
        for (WifiConfiguration configuration : configurations) {
            if (configuration.SSID.equals(ssid)) {
                return configuration;
            }
        }
        return null;
    }

    public static int getSecurity(ScanResult result) {
        if (result.capabilities.contains("WEP")) {
            return SECURITY_WEP;
        } else if (result.capabilities.contains("PSK")) {
            return SECURITY_PSK;
        } else if (result.capabilities.contains("EAP")) {
            return SECURITY_EAP;
        }
        return SECURITY_NONE;
    }

    public static String convertToQuotedString(String string) {
        return "\"" + string + "\"";
    }

    //from com.android.settings.wifi.WifiConfigController

    /**
     * 这里直接是阅读系统设置里面的wifi配置
     * 其实这里可以阅读以下WifiConfiguration,里面关于wifi的加密参数
     *
     * @param accessPointSecurity
     * @param ssid
     * @param password
     * @return
     */
    public static WifiConfiguration getConfig(int accessPointSecurity, String ssid,
                                              String password) {

        WifiConfiguration config = new WifiConfiguration();
        config.SSID = convertToQuotedString(ssid);
        config.hiddenSSID = true;
        switch (accessPointSecurity) {
            case SECURITY_NONE:
                config.allowedKeyManagement.set(KeyMgmt.NONE);
                break;

            case SECURITY_WEP:
                config.allowedKeyManagement.set(KeyMgmt.NONE);
                config.allowedAuthAlgorithms.set(AuthAlgorithm.OPEN);
                config.allowedAuthAlgorithms.set(AuthAlgorithm.SHARED);
                int length = password.length();
                // WEP-40, WEP-104, and 256-bit WEP (WEP-232?)
                if ((length == 10 || length == 26 || length == 58)
                        && password.matches("[0-9A-Fa-f]*")) {
                    config.wepKeys[0] = password;
                } else {
                    config.wepKeys[0] = '"' + password + '"';
                }
                break;

            case SECURITY_PSK:
                config.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
                if (password.length() != 0) {
                    if (password.matches("[0-9A-Fa-f]{64}")) {
                        config.preSharedKey = password;
                    } else {
                        config.preSharedKey = '"' + password + '"';
                    }
                }
                break;

            case SECURITY_EAP:
                //EAP暂时还没不完整
                config.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
                config.allowedKeyManagement.set(KeyMgmt.IEEE8021X);
                break;
            default:
                return null;
        }

        return config;
    }

    public static int addWifiConfiguration(Context context, WifiConfiguration configuration) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return manager.addNetwork(configuration);
    }

    public static void connectWifi(Context context, int networkId) {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        manager.enableNetwork(networkId, true);
    }

}
