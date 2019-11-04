package com.zss.mall.fragment.submit_order;

import com.zss.mall.network.BaseResponse;
import com.zss.mall.network.RetrofitManager;
import com.zss.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubmitOrderModel extends BaseModel {

    void getDefaultAddress(BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.getDefaultAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void submitOrder(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.submitOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
