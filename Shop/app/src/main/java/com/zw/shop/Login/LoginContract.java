package com.zw.shop.Login;

import com.zw.shop.base.IBaseView;
import com.zw.shop.http.response.ShopInfoResponse;

/**
 * Created by ZouWei on 2018/4/27.
 */

public interface LoginContract {

    interface LoginView extends IBaseView{
        void onSuccess(ShopInfoResponse shopInfoBean);
    }

}
