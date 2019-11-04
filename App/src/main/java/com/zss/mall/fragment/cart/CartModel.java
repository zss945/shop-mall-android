package com.zss.mall.fragment.cart;

import android.arch.lifecycle.LifecycleOwner;

import com.zss.mall.BaseApplication;
import com.zss.mall.network.BaseResponse;
import com.zss.mall.network.RetrofitManager;
import com.zss.mall.room.entity.Cart;
import com.zss.ui.mvp.IPresenter;
import com.zss.ui.mvp.base.BaseModel;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

class CartModel extends BaseModel implements IPresenter {

    CompositeDisposable mDisposable = new CompositeDisposable();

    void queryGoodsByLike(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.queryGoodsByLike(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void queryAllCart(Consumer<List<Cart>> consumer) {
        mDisposable.add(BaseApplication.getInstance()
                .getCartDao()
                .queryAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(consumer)
                .subscribe());
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        super.onDestroy(owner);
        mDisposable.clear();
    }
}
