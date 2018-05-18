package com.zw.shop.Register;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.zw.shop.R;
import com.zw.shop.http.HttpApi;
import com.zw.shop.http.model.ShopInfoBean;
import com.zw.shop.http.response.BaseResponse;
import com.zw.shop.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZouWei on 2018/4/12.
 */

public class RegisterActivity extends Activity {

    @BindView(R.id.et_number)
    public EditText mShopNumber;
    @BindView(R.id.et_password)
    public EditText mPassword;
    @BindView(R.id.et_shopName)
    public EditText mShopName;
    @BindView(R.id.et_shopAddress)
    public EditText mShopAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register)
    public void register() {
        String shopNumber = mShopNumber.getText().toString().trim();
        String passWord = mPassword.getText().toString().trim();
        String shopName = mShopName.getText().toString().trim();
        String shopAddress = mShopAddress.getText().toString().trim();
        if (TextUtils.isEmpty(shopNumber)) {
            Toast.makeText(getApplicationContext(), "账号不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(getApplicationContext(), "账号不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        ShopInfoBean shopInfoBean = new ShopInfoBean();
        shopInfoBean.shopNumber = shopNumber;
        shopInfoBean.passWord = passWord;
        shopInfoBean.shopName = shopName;
        shopInfoBean.shopAddress = shopAddress;
        new RegisterManager().register(shopInfoBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToast(getApplicationContext(), "请求失败！");
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        ToastUtils.showToast(getApplicationContext(), "请求成功！");
                    }
                });
    }


}
