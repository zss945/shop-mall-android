package com.zss.mall.network;

import com.zss.mall.bean.ResultBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 网络服务接口
 * @author zm
 */
public interface INetworkService {

    @POST("login")
    Observable<ResultBean> login(@Body Map<String, Object> params);

    @GET("queryTreeCategory")
    Observable<ResultBean> queryTreeCategory(@QueryMap Map<String, Object> params);

    @GET("queryGoodsByCategory")
    Observable<ResultBean> queryGoodsByCategory(@QueryMap Map<String, Object> params);

    @GET("queryGoodsByLike")
    Observable<ResultBean> queryGoodsByLike(@QueryMap Map<String, Object> params);

    @POST("getProductBySpec")
    Observable<ResultBean> getProductBySpec(@Body Map<String, Object> params);

    @POST("saveCart")
    Observable<ResultBean> saveCart(@Header("cartKey") String cartKey, @Body Map<String, Object> params);

    @POST("queryAddressList")
    Observable<ResultBean> queryAddressList();

    @POST("queryCurrentLevelAreaById")
    Observable<ResultBean> queryCurrentLevelAreaById(@Body Map<String, Object> params);

    @POST("queryAreaByLevel")
    Observable<ResultBean> queryAreaByLevel(@Body Map<String, Object> params);

    @POST("queryAreaByParentId")
    Observable<ResultBean> queryAreaByParentId(@Body Map<String, Object> params);

    @POST("saveAddress")
    Observable<ResultBean> saveAddress(@Body Map<String, Object> params);

    @POST("updateAddress")
    Observable<ResultBean> updateAddress(@Body Map<String, Object> params);

    @POST("getDefaultAddress")
    Observable<ResultBean> getDefaultAddress();

    @POST("submitOrder")
    Observable<ResultBean> submitOrder(@Body Map<String, Object> params);

    @POST("alipay")
    Observable<ResultBean> alipay(@Body Map<String, Object> params);

    @POST("queryOrderByStatus")
    Observable<ResultBean> queryOrderByStatus(@Body Map<String, Object> params);

}
