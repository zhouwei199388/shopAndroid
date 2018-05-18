package com.zw.shop.Login;

import com.zw.shop.base.BaseModel;
import com.zw.shop.http.HttpApi;
import com.zw.shop.http.HttpFactory;
import com.zw.shop.http.response.ShopInfoResponse;

import rx.Observable;


/**
 * Created by ZouWei on 2018/4/27.
 */

public class LoginManager extends BaseModel {
    private HttpApi mApi = HttpFactory.getInstance();

    Observable<ShopInfoResponse> login(String userName, String passWord) {
        return mApi.login(userName, passWord);
    }

}
