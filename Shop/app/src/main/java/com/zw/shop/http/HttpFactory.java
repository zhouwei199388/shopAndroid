package com.zw.shop.http;

/**
 * Created by Admin on 2016/5/26.
 */
public class HttpFactory {

    private static final Object o = new Object();
    private static HttpApi mFactory = null;

    public static HttpApi getInstance() {
        synchronized (o) {
            if (mFactory == null) {
                mFactory = new HttpRetrofitSet().getHttpApi();
            }
            return mFactory;
        }
    }
}
