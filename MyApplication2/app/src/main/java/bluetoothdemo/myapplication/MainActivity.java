package bluetoothdemo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bluetoothdemo.myapplication.Bluetooth.BlueControl;
import bluetoothdemo.myapplication.Bluetooth.BluetoothFragment;
import bluetoothdemo.myapplication.guide.GuideFragment;
import bluetoothdemo.myapplication.setting.SettingFragment;

/**
 * Created by ZouWei on 2018/6/2.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private BluetoothFragment mBluetoothFragment;
    private GuideFragment mGuideFragment;
    private SettingFragment mSettingFragment;
//    private FragmentTransaction transaction;


    private TextView mGuideTv;
    private TextView mSettingTv;
    private TextView mBluetoothTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitleText("我的设备");
        setNoBack();

        mGuideTv = findViewById(R.id.tv_guide);
        mBluetoothTv = findViewById(R.id.tv_device);
        mSettingTv = findViewById(R.id.tv_setting);
        mGuideTv.setOnClickListener(this);
        mBluetoothTv.setOnClickListener(this);
        mSettingTv.setOnClickListener(this);
        showGuideFragment();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_guide:
                showGuideFragment();
                break;
            case R.id.tv_device:
                showBluetoothFragment();
                break;
            case R.id.tv_setting:
                showSettingFragment();
                break;
        }
    }

    private void showBluetoothFragment() {

        mGuideTv.setSelected(false);
        mSettingTv.setSelected(false);
        mBluetoothTv.setSelected(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mBluetoothFragment == null) {
            mBluetoothFragment = new BluetoothFragment();
            transaction.add(R.id.fl_container, mBluetoothFragment);
        } else {
            transaction.show(mBluetoothFragment);
        }
        if (mGuideFragment != null) {
            transaction.hide(mGuideFragment);
        }
        if (mSettingFragment != null) {
            transaction.hide(mSettingFragment);
        }
        transaction.commit();
    }

    private void showGuideFragment() {
        mGuideTv.setSelected(true);
        mSettingTv.setSelected(false);
        mBluetoothTv.setSelected(false);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mGuideFragment == null) {
            mGuideFragment = new GuideFragment();
            transaction.add(R.id.fl_container, mGuideFragment);
        } else {
            transaction.show(mGuideFragment);
        }
        if (mBluetoothFragment != null) {
            transaction.hide(mBluetoothFragment);
        }
        if (mSettingFragment != null) {
            transaction.hide(mSettingFragment);
        }
        transaction.commit();
    }

    private void showSettingFragment() {

        mGuideTv.setSelected(false);
        mSettingTv.setSelected(true);
        mBluetoothTv.setSelected(false);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mSettingFragment == null) {
            mSettingFragment = new SettingFragment();
            transaction.add(R.id.fl_container, mSettingFragment);
        } else {
            transaction.show(mSettingFragment);
        }
        if (mBluetoothFragment != null) {
            transaction.hide(mBluetoothFragment);
        }
        if (mGuideFragment != null) {
            transaction.hide(mGuideFragment);
        }
        transaction.commit();
    }


}
