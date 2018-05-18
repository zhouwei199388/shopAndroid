package com.zw.shop.http.request;

import com.zw.shop.BuildConfig;
import com.zw.shop.http.model.RequestHeader;

/**
 * Created by Admin on 2016/5/26.
 */
public class BaseRequest {

    public RequestHeader header;

    public BaseRequest() {
        this.header = initRequestHeaders();
    }

    /**
     * 添加公共请求部分header
     */
    private RequestHeader initRequestHeaders() {
        RequestHeader header = new RequestHeader();
//        header.userToken = MyApplication.mUserToken;
        header.userToken = "";
        header.serviceVersion = getServiceVersion();
        header.clientVersion = getClientVersion();
        header.sourceID = getSourceId();
        header.requestTime = getRequestTime();
        return header;
    }

    /**
     * 获取服务器版本号，现在默认配置为1.0
     */
    private String getServiceVersion() {
        return "1.0";
    }

    private String getClientVersion() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取app渠道号，android为1000
     */
    private String getSourceId() {
        return BuildConfig.CHANNEL;
    }

    private long getRequestTime() {
        return System.currentTimeMillis();
    }
}
