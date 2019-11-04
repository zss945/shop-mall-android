package com.zss.mall.network;

/**
 * 网络参数设置
 * @author zm
 */
public interface INetwork {

    int HTTP_DATA_CACHE_SIZE = 50 * 1024 * 1024; //数据缓存大小 50M

    int HTTP_IMAGE_CACHE_SIZE = 100 * 1024 * 1024; //图片缓存大小 100M

    int HTTP_CONNECT_TIMEOUT = 10; //网络连接超时时间 10s

    int HTTP_READ_TIMEOUT = 30; //网络读曲超时时间 30s

    int HTTP_WRITE_TIMEOUT = 30; //网络写入超时时间 30s

    int HTTP_NETWORK_CACHE_TIME = 20; //获取本地的缓存 20s

    int HTTP_NOT_NETWORK_CACHE_TIME = 7 * 24 * 60 * 60; //离线缓存时间 7天

}
