package com.zss.mall.network;

import com.zss.mall.BaseApplication;
import com.zss.mall.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Retrofit管理
 *
 * @author zm
 */
public class RetrofitManager {

    public INetworkService mNetwrokService;

    private static volatile RetrofitManager manager;

    public static RetrofitManager getInstance() {
        if (manager == null) {
            synchronized (RetrofitManager.class) {
                if (manager == null) {
                    manager = new RetrofitManager();
                }
            }
        }
        return manager;
    }

    private RetrofitManager() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.getBaseUrl())
                    .addConverterFactory(new FastjsonConverterFactory())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
            mNetwrokService = retrofit.create(INetworkService.class);
        } catch (Exception ignored) {
        }
    }


    private OkHttpClient getOkHttpClient() {

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        builder.addInterceptor(httpLoggingInterceptor);
//        builder.connectTimeout(10, TimeUnit.SECONDS);
//        builder.sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager());
//        builder.hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier());
//        builder.retryOnConnectionFailure(true);
//        return builder.build();


        //改造OkHttpClient添加缓存功能
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager());
        builder.hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier());

        //缓存目录设置 （注：缓存只允许GET方式）
        File httpCacheDirectory = new File(BaseApplication.getInstance().getCacheDir(), "HttpCache");
        int cacheSize = INetwork.HTTP_DATA_CACHE_SIZE;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        builder.cache(cache);

        //有网络缓存拦截，20秒内的请求，获取本地的缓存。

        builder.addNetworkInterceptor(new BaseInterceptor.CommonNetworkCache(INetwork.HTTP_NETWORK_CACHE_TIME));

        //无网络缓存拦截，离线缓存时间 7天
        builder.addInterceptor(new BaseInterceptor.CommonNoNetworkCache(INetwork.HTTP_NOT_NETWORK_CACHE_TIME, BaseApplication.getInstance()));

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);

        builder.connectTimeout(INetwork.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(INetwork.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(INetwork.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }
}
