package com.zw.shop.http.model;

/**
 * Created by ZouWei on 2018/5/8.
 */
public class RequestHeader {
    /**用户Token，登录后服务端返回，未登录时传“”*/
    public String userToken;
    /**服务器版本号*/
    public String serviceVersion;
    /**app版本号*/
    public String clientVersion;
    /**App渠道号，Android：1000，iOS：2000*/
    public String sourceID;
    /**App系统时间*/
    public long requestTime;
}
