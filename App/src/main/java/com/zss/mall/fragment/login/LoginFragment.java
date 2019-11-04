package com.zss.mall.fragment.login;

import android.os.Bundle;
import android.view.View;

import com.zss.mall.R;
import com.zss.mall.activity.home.HomeActivity;
import com.zss.ui.fragment.BaseFragment;
import com.zss.ui.row.RowInputEdit;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    @BindView(R.id.mobile)
    RowInputEdit mMobile;

    @BindView(R.id.password)
    RowInputEdit mPassword;

    @Override
    public int getViewId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        getToolbar().setTitle("登录");
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(new LoginModel(), this);
    }


    @Override
    public void goHome() {
        goIntent(HomeActivity.class);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.login, R.id.register, R.id.forget_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                getPresenter().login(mMobile.getText(), mPassword.getText());
                break;
            case R.id.register:
                break;
            case R.id.forget_pwd:
                break;
        }
    }

}
