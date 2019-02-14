package com.zw.shop.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.zw.shop.R;
import com.zw.shop.Register.RegisterActivity;
import com.zw.shop.http.response.ShopInfoResponse;
import com.zw.shop.menu.MainActivity;
import com.zw.shop.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZouWei on 2018/4/12.
 */

public class LoginActivity extends Activity implements LoginContract.LoginView {

    @BindView(R.id.et_phone)
    public EditText mPhoneEt;
    @BindView(R.id.et_password)
    public EditText mPasswordEt;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
    }


    @OnClick(R.id.btn_login)
    public void login() {
        String userName = mPhoneEt.getText().toString().trim();
        String passWord = mPasswordEt.getText().toString().trim();
        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             TextUtils.isEmpty(s.toString());
            }
        });
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passWord.length() < 6) {
            Toast.makeText(this, "密码不能小于六位", Toast.LENGTH_SHORT).show();
            return;
        }
        mLoginPresenter.login(userName, passWord);
    }

    @OnClick(R.id.to_register)
    public void toRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }


    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(ShopInfoResponse shopInfoBean) {
        SPUtils.setParam(this, "isLogin", true);
        startActivity(new Intent(this, MainActivity.class));
    }
}
