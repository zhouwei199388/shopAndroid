package bluetoothdemo.myapplication.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import bluetoothdemo.myapplication.Bluetooth.callback.ConnectCallback;
import bluetoothdemo.myapplication.Bluetooth.callback.OnReceiverCallback;
import bluetoothdemo.myapplication.Bluetooth.callback.OnWriteCallback;
import bluetoothdemo.myapplication.Bluetooth.request.ReceiverRequestQueue;

import static bluetoothdemo.myapplication.MyApplication.TAG;

/**
 * Created by ZouWei on 2018/5/30.
 */
public class BlueControl {

    public static final int REQUEST_ENABLE_BT = 11111;

    //默认连接超时时间:5s
    private static final int CONNECTION_TIME_OUT = 10000;

    //此属性一般不用修改
    private static final String BLUETOOTH_NOTIFY_D = "00002902-0000-1000-8000-00805f9b34fb";


    //TODO 以下uuid根据公司硬件改变
    public static final String UUID_SERVICE = "000000ff-0000-1000-8000-00805f9b34fb";
    public static final String UUID_INDICATE = "0000000-0000-0000-8000-00805f9b0000";
    public static final String UUID_NOTIFY = "00002a05-0000-1000-8000-00805f9b34fb";
    public static final String UUID_WRITE = "0000ff01-0000-1000-8000-00805f9b34fb";
    public static final String UUID_READ = "3f3e3d3c-3b3a-3938-3736-353433323130";


    //是否是用户手动断开
    private boolean isMybreak = false;
    //连接请求是否ok
    private boolean isConnectok;

    private boolean isConnected;
    private String connectedAdress;
    private BluetoothGatt mBleGatt;
    private BluetoothDevice remoteDevice;

    private BleGattCallBack mGattCallback;
    private OnWriteCallback writeCallback;
    private BluetoothGattCharacteristic mBleGattCharacteristic;

    //获取到所有服务的集合
    private HashMap<String, Map<String, BluetoothGattCharacteristic>> servicesMap = new HashMap<>();
    private ConnectCallback mConnectCallBack;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private ReceiverRequestQueue mReceiverRequestQueue = new ReceiverRequestQueue();
    private String mAddress;
    private static class lazyHolder {
        private static BlueControl INSTANCE = new BlueControl();
    }

    public static BlueControl getInstance() {
        return lazyHolder.INSTANCE;
    }


    private BluetoothAdapter mBluetoothAdapter;

    private Context mContext;

    /**
     * 初始化BluetoothAdapter;
     *
     * @param context
     */
    public void initBlueAdapter(Context context) {
        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        mGattCallback = new BleGattCallBack();
        this.mContext = context;
    }


    /**
     * 打开蓝牙
     *
     * @param context
     */
    public void openBluetooth(Activity context) {
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBlueIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            context.startActivityForResult(enableBlueIntent, REQUEST_ENABLE_BT);
        }
    }


    /**
     * 蓝牙是否打开
     *
     * @return
     */
    public boolean isEnabled() {
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter.isEnabled();
        }
        return false;
    }


    /**
     * 是否搜索中
     *
     * @return
     */
    public boolean isDiscovering() {
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter.isDiscovering();
        }
        return false;
    }


    /**
     * 搜索蓝牙
     */
    public void startDiscovery() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.startDiscovery();
        }
    }

    /**
     * 停止扫描
     */
    public void cancelDiscovery() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    /**
     * 手动断开Ble连接
     */
    public void disConnectBleConn() {
        disConnection();
        isMybreak = true;
        isConnected = false;
    }

    /**
     * 连接设备
     *
     * @param connectionTimeOut 指定连接超时
     * @param address           设备mac地址
     * @param connectCallback   连接回调
     */


    public void Connect(Context context, final int connectionTimeOut, final String address, ConnectCallback connectCallback) {
        if (mBluetoothAdapter == null || address == null) {
            Log.e(TAG, "No device found at this address：" + address);
            return;
        }
        remoteDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (remoteDevice == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return;
        }
        this.mConnectCallBack = connectCallback;
        if (mBleGatt != null) {
            mBleGatt.disconnect();
        }

        mBleGatt = remoteDevice.connectGatt(context, false, mGattCallback);
        Log.e(TAG, "connecting mac-address:" + address);
//        delayConnectResponse(connectionTimeOut);
    }

    /**
     * 连接设备
     *
     * @param address         设备mac地址
     * @param connectCallback 连接回调
     */
    public void Connect(final String address, Context context, ConnectCallback connectCallback) {
        this.mAddress = address;
        Connect(context, CONNECTION_TIME_OUT, address, connectCallback);
    }

    /**
     * 断开连接
     */
    private void disConnection() {
        if (null == mBluetoothAdapter || null == mBleGatt) {
            Log.e(TAG, "disconnection error maybe no init");
            return;
        }
        mBleGatt.disconnect();
        reset();
    }

    /**
     * 重置数据
     */
    private void reset() {
        isConnectok = false;
        Log.e(TAG, "isConnectok    not ok");
//        servicesMap.clear();
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void runOnMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            if (mHandler != null) {
                mHandler.post(runnable);
            }
        }
    }


    /**
     * 发送数据
     *
     * @param value         指令
     * @param writeCallback 发送回调
     */
    public void WriteBuffer(String value, OnWriteCallback writeCallback) {
        this.writeCallback = writeCallback;
        if (!isEnabled()) {
            writeCallback.onFailed(OnWriteCallback.FAILED_BLUETOOTH_DISABLE);
            Log.e(TAG, "蓝牙未打开");
            return;
        }

        mBleGattCharacteristic = getBluetoothGattCharacteristic(UUID_SERVICE, UUID_WRITE);

        if (null == mBleGattCharacteristic) {
            writeCallback.onFailed(OnWriteCallback.FAILED_INVALID_CHARACTER);
            Log.e(TAG, "Characteristic 为空");
            return;
        }

//        mBleGattCharacteristic.setValue(HexUtil.hexStringToBytes(value)); //不使用此方法  只需 直接getBytes
        mBleGattCharacteristic.setValue(value.getBytes());
        //发送

        boolean b = mBleGatt.writeCharacteristic(mBleGattCharacteristic);

        Log.e(TAG, "send:" + b + "data：" + value);
    }

    /**
     * 根据服务UUID和特征UUID,获取一个特征{@link BluetoothGattCharacteristic}
     *
     * @param serviceUUID   服务UUID
     * @param characterUUID 特征UUID
     */
    private BluetoothGattCharacteristic getBluetoothGattCharacteristic(String serviceUUID, String characterUUID) {
        if (!isEnabled()) {
            throw new IllegalArgumentException(" Bluetooth is no enable please call BluetoothAdapter.enable()");
        }
        if (null == mBleGatt) {
            Log.e(TAG, "mBluetoothGatt is null");
            return null;
        }

        //找服务
        Map<String, BluetoothGattCharacteristic> bluetoothGattCharacteristicMap = servicesMap.get(serviceUUID);
        if (null == bluetoothGattCharacteristicMap) {
            Log.e(TAG, "Not found the serviceUUID!");
            return null;
        }

        //找特征
        Set<Map.Entry<String, BluetoothGattCharacteristic>> entries = bluetoothGattCharacteristicMap.entrySet();
        BluetoothGattCharacteristic gattCharacteristic = null;
        for (Map.Entry<String, BluetoothGattCharacteristic> entry : entries) {
            if (characterUUID.equals(entry.getKey())) {
                gattCharacteristic = entry.getValue();
                break;
            }
        }
        return gattCharacteristic;
    }


    private class BleGattCallBack extends BluetoothGattCallback {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (newState == BluetoothProfile.STATE_CONNECTED) { //连接成功
                isMybreak = false;
                isConnectok = true;
                Log.e(TAG, "链接成功");
                mBleGatt.discoverServices();
                connSuccess();
                connectedAdress = gatt.getDevice().getAddress();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {   //断开连接
                Log.e(TAG, "断开连接");
                isConnected = false;
                connectedAdress = null;
                mBleGatt.close();
                if (!isMybreak) {
                    Connect(mContext, CONNECTION_TIME_OUT, mAddress, mConnectCallBack);
                }
                reset();
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if (null != writeCallback) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            writeCallback.onSuccess();
                        }
                    });
                    Log.e(TAG, "发送成功!");
                } else {
                    runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            writeCallback.onFailed(OnWriteCallback.FAILED_OPERATION);
                        }
                    });
                    Log.e(TAG, "发送失败!");
                }
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            if (null != mReceiverRequestQueue) {
                HashMap<String, OnReceiverCallback> map = mReceiverRequestQueue.getMap();
                final byte[] rec = characteristic.getValue();
                for (String key : mReceiverRequestQueue.getMap().keySet()) {
                    final OnReceiverCallback onReceiverCallback = map.get(key);
                    runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            onReceiverCallback.onReceiver(rec);
                        }
                    });
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (null != mBleGatt && status == BluetoothGatt.GATT_SUCCESS) {
                Log.e(TAG, "onServicesDiscovered");
                List<BluetoothGattService> services = mBleGatt.getServices();
                for (int i = 0; i < services.size(); i++) {
                    HashMap<String, BluetoothGattCharacteristic> charMap = new HashMap<>();
                    BluetoothGattService bluetoothGattService = services.get(i);
                    String serviceUuid = bluetoothGattService.getUuid().toString();
                    Log.e(TAG, serviceUuid);
                    List<BluetoothGattCharacteristic> characteristics = bluetoothGattService.getCharacteristics();
                    for (int j = 0; j < characteristics.size(); j++) {
                        Log.d(TAG,Arrays.toString(characteristics.get(j).getValue()));

                        charMap.put(characteristics.get(j).getUuid().toString(), characteristics.get(j));
                    }
                    servicesMap.put(serviceUuid, charMap);
                }
                Log.e(TAG, "servicesMap size" + servicesMap.size());
                BluetoothGattCharacteristic NotificationCharacteristic = getBluetoothGattCharacteristic(UUID_SERVICE, UUID_NOTIFY);
                if (NotificationCharacteristic == null)
                    return;
                Log.e(TAG, "enableNotification");

                enableNotification(true, NotificationCharacteristic);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);

            Log.e(TAG, "onCharacteristicRead");
        }
    }

    /**
     * 设置通知
     *
     * @param enable         true为开启false为关闭
     * @param characteristic 通知特征
     * @return
     */
    private boolean enableNotification(boolean enable, BluetoothGattCharacteristic characteristic) {
        if (mBleGatt == null || characteristic == null)
            return false;
        if (!mBleGatt.setCharacteristicNotification(characteristic, enable))
            return false;
        BluetoothGattDescriptor clientConfig = characteristic.getDescriptor(UUID.fromString(BLUETOOTH_NOTIFY_D));
        if (clientConfig == null)
            return false;

        if (enable) {
            clientConfig.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            clientConfig.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        return mBleGatt.writeDescriptor(clientConfig);
    }

    // TODO 此方法Notify成功时会被调用。可在通知界面连接成功,内部代码可自行修改，如发送广播
    private void connSuccess() {
        if (mConnectCallBack != null) {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    mConnectCallBack.onConnSuccess();
                }
            });
        }
        Log.e(TAG, "Ble connect success!");
    }
}
