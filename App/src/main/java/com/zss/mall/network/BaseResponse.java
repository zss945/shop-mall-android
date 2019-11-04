package com.zss.mall.network;

import com.zss.kit.NetworkUtils;
import com.zss.kit.ToastUtils;
import com.zss.mall.BaseApplication;
import com.zss.mall.bean.ResultBean;
import com.zss.mall.kit.AppUtils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 公用返回、错误处理
 *
 * @author zm
 */
public abstract class BaseResponse implements Observer<ResultBean> {

    private Disposable mDisposable;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(ResultBean bean) {
        if (bean.getCode() == 0) {
            onSuccess(bean);
        } else if (bean.getCode() == 401) {
            AppUtils.exitLogin();
        } else {
            ToastUtils.showToast(bean.getError());
            onError(bean.getError());
        }
    }

    @Override
    public void onError(Throwable e) {
        String errMsg;
        if (!NetworkUtils.isAvailable(BaseApplication.getInstance())) {
            errMsg = "网络连接异常";
        } else if (e instanceof HttpException) {
            errMsg = "网络请求异常";
        } else if (e instanceof IOException) {
            errMsg = "处理数据异常";
        } else {
            errMsg = e.getMessage();
        }
        onError(errMsg);
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(ResultBean bean);

    public abstract void onError(String errMsg);
}
