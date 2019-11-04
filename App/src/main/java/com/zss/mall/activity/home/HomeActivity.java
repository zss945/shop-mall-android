package com.zss.mall.activity.home;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;

import com.zss.mall.R;
import com.zss.mall.bean.Event;
import com.zss.mall.fragment.cart.CartFragment;
import com.zss.mall.fragment.goods.GoodsFragment;
import com.zss.mall.fragment.me.MeFragment;
import com.zss.ui.activity.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeView{

    @BindView(R.id.nav_view)
    BottomNavigationView mNavView;

    int mPrevIndex = 0;
    List<Fragment> mFragmentList;

    Badge mBdage;

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                switchFragmentPosition(0);
                return true;
            case R.id.navigation_cart:
                switchFragmentPosition(1);
                return true;
            case R.id.navigation_me:
                switchFragmentPosition(2);
                return true;
        }
        return false;
    };

    @Override
    public int getViewId() {
        return R.layout.activity_home;
    }


    public void initView(Bundle savedInstanceState) {
        mNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(new HomeModel(), this);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new GoodsFragment());
        mFragmentList.add(new CartFragment());
        mFragmentList.add(new MeFragment());
        switchFragmentPosition(0);
        BottomNavigationMenuView menuView = null;
        for (int i = 0; i < mNavView.getChildCount(); i++) {
            View child = mNavView.getChildAt(i);
            if (child instanceof BottomNavigationMenuView) {
                menuView = (BottomNavigationMenuView) child;
                break;
            }
        }
        if (menuView != null) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1);
            mBdage = new QBadgeView(getBaseContext()).bindTarget(itemView);
            mBdage.setBadgeGravity(Gravity.TOP | Gravity.END);
            mBdage.setGravityOffset(25, 0, true);
        }
        getPresenter().getBadgeCount();
    }

    private void switchFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragmentList.get(position);
        Fragment prevFragment = mFragmentList.get(mPrevIndex);
        mPrevIndex = position;
        ft.hide(prevFragment);
        if (!currentFragment.isAdded()) {
            ft.add(R.id.frame_layout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.CartEvent event) {
        getPresenter().getBadgeCount();
    }

    @Override
    public void setBadgeCount(Integer count) {
        mBdage.setBadgeNumber(count);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
