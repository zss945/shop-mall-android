package com.zss.mall.fragment.address;

import com.zss.mall.network.BaseResponse;
import com.zss.mall.network.RetrofitManager;
import com.zss.ui.mvp.base.BaseModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressModel extends BaseModel {

    void queryAddressList(BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.queryAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
