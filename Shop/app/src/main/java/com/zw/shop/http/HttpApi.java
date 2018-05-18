package com.zw.shop.http;


import com.zw.shop.http.model.ShopInfoBean;
import com.zw.shop.http.response.BaseResponse;
import com.zw.shop.http.response.ShopInfoResponse;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Admin on 2016/5/26.
 */
public interface HttpApi {

    @GET(HttpConstance.LOGIN)
    Observable<ShopInfoResponse> login(@Query("userName") String userName, @Query("passWord") String passWord);


    @POST(HttpConstance.REGISTER)
    Observable<BaseResponse> register(@Body ShopInfoBean shopInfo);

}
