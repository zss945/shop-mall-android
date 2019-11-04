package com.zss.mall.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 图片缓存模版
 * @author zm
 */
@GlideModule
public class GlideModelConfig extends AppGlideModule {

    public static OkHttpClient getHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(INetwork.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        client.sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager());
        client.hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier());
        return client.build();
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        int diskSize = INetwork.HTTP_IMAGE_CACHE_SIZE;
        int memorySize = (int) (Runtime.getRuntime().maxMemory()) / 8;
        builder.setMemoryCache(new LruResourceCache(memorySize));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskSize));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getHttpClient()));
    }

}