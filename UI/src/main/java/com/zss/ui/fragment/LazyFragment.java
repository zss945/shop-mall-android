package com.zss.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zss.ui.activity.ToolbarFragmentActivity;
import com.zss.ui.mvp.IPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 懒加载Fragment基类
 * @param <P>
 */
public abstract class LazyFragment<P extends IPresenter> extends Fragment {

    private Unbinder mUnbinder;

    private P mPresenter;

    /**
     * 是否可见状态
     */
    private boolean isVisible;

    /**
     * 标志位，View已经初始化完成。
     */
    private boolean isPrepared;

    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isFirstLoad = true;
        View rootView = inflater.inflate(getViewId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        initView(savedInstanceState);
        mPresenter = createPresenter();
        isPrepared = true;
        lazyLoad();
        return rootView;
    }

    /**
     * 与ViewPager一起使用会调用
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * FragmentTransaction先隐藏后显示会调用
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
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
            throw new RuntimeException("parent activity is null");
        }
    }

    public void goIntent(Class cls){
        goIntent(cls, null);
    }

    public void goIntent(Class cls, Bundle args){
        Intent intent = new Intent(getActivity(), cls);
        if (args != null) {
            intent.putExtras(args);
        }
        startActivity(intent);
    }
}
