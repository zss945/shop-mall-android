package com.zss.mall.fragment.goods_detail;

import com.zss.mall.BaseApplication;
import com.zss.ui.mvp.base.BaseModel;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class GoodsDetailModel extends BaseModel {

    void getBadgeCount(SingleObserver<Integer> observer) {
        BaseApplication.getInstance()
                .getCartDao()
                .getBadgeCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
