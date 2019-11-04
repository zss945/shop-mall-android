package com.zss.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zss.ui.activity.BaseFragmentActivity;
import com.zss.ui.activity.ToolbarFragmentActivity;
import com.zss.ui.mvp.IPresenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 *
 * @param <P>
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment {

    private Unbinder mUnbinder;
    private P mPresenter;
    private boolean supportEventBus = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getViewId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initView(savedInstanceState);
        mPresenter = createPresenter();
        initData();
        return rootView;
    }

    public abstract int getViewId();

    public abstract void initView(Bundle savedInstanceState);

    public abstract void initData();

    public abstract P createPresenter();

    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    public P getPresenter() {
        return mPresenter;
    }

    public Toolbar getToolbar() {
        if (getActivity() != null) {
            if (getActivity() instanceof ToolbarFragmentActivity) {
                ToolbarFragmentActivity activity = (ToolbarFragmentActivity) getActivity();
                return activity.getToolbar();
            } else {
                throw new RuntimeException("activity not is ToolbarActivity or ToolbarFragmentActivity");
            }
        } else {
            throw new RuntimeException("activity is null");
        }
    }

    public void goIntent(Class cls) {
        goIntent(cls, null);
    }

    public void goIntent(Class cls, Bundle args) {
        Intent intent = new Intent(getActivity(), cls);
        if (args != null) {
            intent.putExtras(args);
        }
        startActivity(intent);
    }

    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void setSupportEventBus() {
        this.supportEventBus = true;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (this.supportEventBus) {
            EventBus.getDefault().unregister(this);
        }
    }

    public <T extends View> T findViewById(int id) {
        if(getView() != null){
            return getView().findViewById(id);
        } else {
            return null;
        }
    }
}
