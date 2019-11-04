package com.zss.mall.activity.splash;

import android.os.Bundle;

import com.zss.mall.R;
import com.zss.mall.activity.home.HomeActivity;
import com.zss.ui.activity.base.BaseActivity;
import com.zss.ui.mvp.IPresenter;

public class SplashActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @Override
    public void initData() {
        goIntent(HomeActivity.class);
        finish();
    }

}
