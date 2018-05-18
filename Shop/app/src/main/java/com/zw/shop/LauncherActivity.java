package com.zw.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.zw.shop.Login.LoginActivity;
import com.zw.shop.menu.MainActivity;
import com.zw.shop.utils.SPUtils;

/**
 * Created by ZouWei on 2018/5/9.
 */
public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Boolean isLogin = (Boolean) SPUtils.getParam(getApplicationContext(), "isLogin", false);
        if (isLogin) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
