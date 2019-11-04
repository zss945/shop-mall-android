package com.zss.mall.activity.select_spec;

import com.zss.mall.BaseApplication;
import com.zss.mall.network.BaseResponse;
import com.zss.mall.network.RetrofitManager;
import com.zss.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.FlowableSubscriber;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SelectSpecModel extends BaseModel {

    void getProductBySpec(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.getProductBySpec(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void getKeyCart(SingleObserver<String> observer) {
        BaseApplication.getInstance()
                .getCartDao()
                .getCartKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void saveCart(String cartKey, Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.saveCart(cartKey, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
