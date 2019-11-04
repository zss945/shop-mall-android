package com.zss.mall.fragment.login;

import com.zss.mall.network.BaseResponse;
import com.zss.mall.network.RetrofitManager;
import com.zss.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class LoginModel extends BaseModel {

    void login(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
