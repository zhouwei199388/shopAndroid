package com.zw.shop.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zw.shop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZouWei on 2018/5/10.
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_add)
    public void toAddMenu() {
        startActivity(new Intent(this, AddMenuActivity.class));
    }
}
