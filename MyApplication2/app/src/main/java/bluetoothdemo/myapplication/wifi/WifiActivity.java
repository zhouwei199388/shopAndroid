package bluetoothdemo.myapplication.wifi;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import bluetoothdemo.myapplication.R;


public class WifiActivity extends AppCompatActivity {

    private WifiReceiver mWifiReceiver;
    private ScanReceiver mScanReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bluetooth);
        //启动定位权限
        requestLocationPermission();
        mWifiReceiver = new WifiReceiver();
        mScanReceiver = new ScanReceiver();
        sendWifiReceiver();
        sendScanReceiver();
    }

    public void onClick_Search(View view) {
        if (WifiHelper.isWifiOpen(this)) {
            Toast.makeText(this, "已经打开", Toast.LENGTH_SHORT).show();
            //获取wifi列表
            WifiHelper.startScan(this);

        } else {
            WifiHelper.setWifiEnabled(this, true);
        }
    }

    /**
     * 注册wifi状态监听广播
     */
    private void sendWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    /**
     * 注册获取列表广播
     */
    private void sendScanReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(mScanReceiver, filter);
    }



    /**
     * wifi状态广播
     */
    public class WifiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1111);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        //已关闭
                        Toast.makeText(context, "Wifi未打开", Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        //关闭中
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        //已连接
                        Toast.makeText(context, "Wifi已打开", Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        //wifi链接中
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        //未知状态
                }
            }
    }

    /**
     * 获取wifi列表广播
     */
    public static class ScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            intent.getDataString();
//            List<ScanResult> scanResults = WifiHelper.getScanResults(context);
//            Toast.makeText(context, scanResults.size() + "", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 定位权限申请
     */
    public void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
            //判断是否具有权限
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //判断是否需要向用户解释为什么需要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(this, "自Android 6.0开始需要打开位置权限才可以搜索到WIFI设备", Toast.LENGTH_SHORT);

                }
                //请求权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        100);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScanReceiver);
        unregisterReceiver(mWifiReceiver);
    }
}
