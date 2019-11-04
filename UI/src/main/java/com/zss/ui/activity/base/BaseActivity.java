package com.zss.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zss.ui.mvp.IPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 *
 * @param <P>
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity {

    private Unbinder mUnbinder;
    private P mPresenter;
    private boolean supportEventBus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        mUnbinder = ButterKnife.bind(this);
        initView(savedInstanceState);
        mPresenter = createPresenter();
        initData();
    }

    public abstract int getViewId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract P createPresenter();

    public abstract void initData();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
    }

    public void setPresenter(P mPresenter) {
        this.mPresenter = mPresenter;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public void goIntent(Class cls){
        goIntent(cls, null);
    }

    public void goIntent(Class cls, Bundle args){
        Intent intent = new Intent(this, cls);
        if (args != null) {
            intent.putExtras(args);
        }
        startActivity(intent);
    }

    public void setSupportEventBus() {
        supportEventBus = true;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter = null;
        }
        if (supportEventBus){
            EventBus.getDefault().unregister(this);
        }
    }

}
