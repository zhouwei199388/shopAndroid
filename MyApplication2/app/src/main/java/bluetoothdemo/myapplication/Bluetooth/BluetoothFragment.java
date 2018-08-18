package bluetoothdemo.myapplication.Bluetooth;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import bluetoothdemo.myapplication.Bluetooth.callback.ConnectCallback;
import bluetoothdemo.myapplication.Bluetooth.callback.OnItemClickListener;
import bluetoothdemo.myapplication.Bluetooth.callback.OnWriteCallback;
import bluetoothdemo.myapplication.MyApplication;
import bluetoothdemo.myapplication.R;
import bluetoothdemo.myapplication.dialog.BaseDialogBuild;

/**
 * Created by ZouWei on 2018/5/30.
 */
public class BluetoothFragment extends Fragment implements OnItemClickListener {
    private BlueControl mBlueControl;
    private BluetoothBroadReceiver mBlueReceiver;
    private RecyclerView mRecyclerView;
    private BlueAdapter mBlueAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        register();
        initBlueControl();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);

        view.findViewById(R.id.device).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),BluetoothActivity.class));
            }
        });

        if (mBlueControl.isEnabled()) {
            if (!mBlueControl.isDiscovering()) {
                mBlueControl.startDiscovery();
            } else {
                Toast.makeText(getContext(), "正在搜索中！", Toast.LENGTH_SHORT).show();
            }
        } else {
            new BaseDialogBuild(getContext())
                    .setButton(1, "打开蓝牙", new BaseDialogBuild.OnDialogClickListener() {
                        @Override
                        public void onClick(Dialog dialog) {
                            mBlueControl.openBluetooth(getActivity());
                            mBlueControl.startDiscovery();
                            dialog.dismiss();
                        }
                    })
                    .setDialogTitle("请注意")
                    .setDialogMessage("请先开启蓝牙，搜索设备...")
                    .show();
        }

        initRecyclerView();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(MyApplication.TAG,"fragment open back");
        if(requestCode==BlueControl.REQUEST_ENABLE_BT){
            if(requestCode== Activity.RESULT_OK){
                Log.d(MyApplication.TAG,"fragment open ok");
            }
        }
    }

    public void onClick_Discovery(View view) {
        mBlueControl.WriteBuffer("ceshi", new OnWriteCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "发送成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(int state) {
                Toast.makeText(getContext(), "发送失败！", Toast.LENGTH_SHORT).show();
            }
        });
//        mBlueControl.disConnectBleConn();
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBlueAdapter = new BlueAdapter();
        mBlueAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mBlueAdapter);
    }

    /**
     * 初始化蓝牙控制类
     */
    private void initBlueControl() {
        mBlueControl = mBlueControl.getInstance();
        mBlueControl.initBlueAdapter(getContext());
    }

    /**
     * 注册广播
     */
    private void register() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        mBlueReceiver = new BluetoothBroadReceiver();
        getContext().registerReceiver(mBlueReceiver, intentFilter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mBlueReceiver);
    }

    /**
     * 蓝牙连接结果回调
     *
     * @param address
     */
    @Override
    public void connect(String address) {
        startActivity(new Intent(getContext(),BluetoothActivity.class));
//        mBlueControl.Connect(address, getContext(), new ConnectCallback() {
//            @Override
//            public void onConnSuccess() {
//                Toast.makeText(getContext(), "连接成功！", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onConnFailed() {
//                Toast.makeText(getContext(), "连接失败！", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    /**
     * 搜索蓝牙接收广播
     */
    private class BluetoothBroadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    Log.d(MyApplication.TAG, "开始搜索蓝牙。。。");
                    mBlueAdapter.clearBlueDevices();
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    Log.d(MyApplication.TAG, "结束搜索蓝牙。。。");
                    mBlueAdapter.notifyDataSetChanged();
                    mBlueControl.cancelDiscovery();
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    Log.d(MyApplication.TAG, "找到蓝牙设备。。。");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    mBlueAdapter.addDate(device);
                    Log.d(MyApplication.TAG, device.getName() + "   " + device.getAddress() + "  " + device.getType());
                    break;
            }
        }
    }

}
