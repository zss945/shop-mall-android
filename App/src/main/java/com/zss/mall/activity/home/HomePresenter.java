package com.zss.mall.activity.home;

import com.zss.ui.mvp.base.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class HomePresenter extends BasePresenter<HomeModel, HomeView> {

    public HomePresenter(HomeModel mModel, HomeView mView) {
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
