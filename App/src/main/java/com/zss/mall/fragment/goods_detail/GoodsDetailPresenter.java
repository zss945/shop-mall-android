package com.zss.mall.fragment.goods_detail;

import com.zss.ui.mvp.base.BasePresenter;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

class GoodsDetailPresenter extends BasePresenter<GoodsDetailModel, GoodsDetailView> {

    GoodsDetailPresenter(GoodsDetailModel mModel, GoodsDetailView mView) {
        super(mModel, mView);
    }

    void getBadgeCount() {
        getModel().getBadgeCount(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(Integer count) {
                getView().setBadgeCount(count);
            }

            @Override
            public void onError(Throwable e) {
                getView().setBadgeCount(0);
            }
        });
    }
}
