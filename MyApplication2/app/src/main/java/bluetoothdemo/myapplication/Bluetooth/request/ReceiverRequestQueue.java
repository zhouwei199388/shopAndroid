package bluetoothdemo.myapplication.Bluetooth.request;

import android.util.Log;


import java.util.HashMap;

import bluetoothdemo.myapplication.Bluetooth.callback.OnReceiverCallback;

/**
 * 描述:接收通知数据请求队列
 */
public class ReceiverRequestQueue implements IRequestQueue<OnReceiverCallback> {
    private static final String TAG = "ReceiverRequestQueue";
    HashMap<String, OnReceiverCallback> map = new HashMap<>();

    @Override
    public void set(String key, OnReceiverCallback onReceiver) {
        map.put(key, onReceiver);
    }

    @Override
    public OnReceiverCallback get(String key) {
        return map.get(key);
    }

    public HashMap<String, OnReceiverCallback> getMap() {
        return map;
    }

    /**
     * 移除一个元素
     *
     * @param key
     */
    public boolean removeRequest(String key) {
        Log.d(TAG, "ReceiverRequestQueue before:" + map.size());
        OnReceiverCallback onReceiverCallback = map.remove(key);
        Log.d(TAG, "ReceiverRequestQueue after:" + map.size());
        return null == onReceiverCallback;
    }
}
