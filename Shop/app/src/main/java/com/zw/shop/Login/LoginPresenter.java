package com.zw.shop.Login;

import android.util.Log;

import com.zw.shop.MyApplication;
import com.zw.shop.base.BasePresenter;
import com.zw.shop.http.response.ShopInfoResponse;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ZouWei on 2018/4/27.
 */

public class LoginPresenter extends BasePresenter<LoginManager,LoginContract.LoginView> {

    public LoginPresenter(LoginContract.LoginView view) {
        super(new LoginManager(), view);
    }

    public void login(String userName ,String passWord){
        mModel.login(userName,passWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShopInfoResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(MyApplication.LOG_STRING,e.toString());
                        mView.showError("服务器错误");
                    }

                    @Override
                    public void onNext(ShopInfoResponse shopInfoBean) {
                            mView.onSuccess(shopInfoBean);
                    }
                });
    }
}
