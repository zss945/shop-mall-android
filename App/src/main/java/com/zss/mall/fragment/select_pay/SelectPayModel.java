package com.zss.mall.fragment.select_pay;

import com.zss.mall.network.BaseResponse;
import com.zss.mall.network.RetrofitManager;
import com.zss.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectPayModel extends BaseModel {

    void alipay(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.alipay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
