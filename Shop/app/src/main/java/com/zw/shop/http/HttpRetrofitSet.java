package com.zw.shop.http;


import com.zw.shop.BuildConfig;
import com.zw.shop.http.request.BaseRequest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 2016/5/26.
 */
public class HttpRetrofitSet {
    HttpApi mHttpApi;
    HttpRetrofitSet() {
        //设置okhttp builder
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.writeTimeout(5, TimeUnit.SECONDS);//设置响应超时

        //设置log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(loggingInterceptor);
        //设置请求头
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.218:8080/")
//                .baseUrl("http://java.shopnctest.com/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mHttpApi = retrofit.create(HttpApi.class);
    }

    public HttpApi getHttpApi() {
        return mHttpApi;
    }
}
