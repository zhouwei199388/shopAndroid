package com.zw.shop.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    OkHttpClient client;

    /**
     * 初始化client
     *
     *   通过client builder初始化client中的一些默认参数
     *   可自行通过方法修改builder中的默认参数
     *   比如  拦截器  请求超时   写入超时  读取超时
     *   dns  网络协议版本   cookie 等
     */
    public OkHttpUtils(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            }
        });//设置拦截器
        builder.connectTimeout(120L, TimeUnit.SECONDS);//设置链接超时
        client = builder.build();//返回OkHttpClient对象
    }

    /**
     *   初始化request  请求体
     *
     *    通过request builder 构造器实例化一个默认GET请求的请求体
     *
     *    可修改head  url 请求方式
     *
     */
    public void getClient(){
        String url ="";
        Request request = new Request.Builder()
                .url(url).build();
        Call call = client.newCall(request);
        try {
           Response response =  call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        try {
            Response response = call.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  void postClient(){
        String url = "";
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = client.newCall(request);
        try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
