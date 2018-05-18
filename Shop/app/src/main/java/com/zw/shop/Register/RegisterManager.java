package com.zw.shop.Register;


import com.zw.shop.http.HttpApi;
import com.zw.shop.http.HttpFactory;
import com.zw.shop.http.model.ShopInfoBean;
import com.zw.shop.http.response.BaseResponse;

import rx.Observable;

/**
 * Created by ZouWei on 2018/5/9.
 */
public class RegisterManager {

    private HttpApi mApi = HttpFactory.getInstance();

    Observable<BaseResponse> register(ShopInfoBean shopInfo) {
        return mApi.register(shopInfo);
    }
}
