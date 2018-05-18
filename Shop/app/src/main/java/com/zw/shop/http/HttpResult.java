package com.zw.shop.http;

/**
 * Created by Admin on 2016/6/3.
 */
public interface HttpResult<T>{
    void onSuccess(T result);
    void onFail();
}
