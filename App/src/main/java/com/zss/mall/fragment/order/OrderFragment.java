package com.zss.mall.fragment.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.zss.mall.Constants;
import com.zss.mall.R;
import com.zss.mall.fragment.order_list.OrderListFragment;
import com.zss.ui.adapter.viewpager.BaseFragmentPagerAdapter;
import com.zss.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderFragment extends BaseFragment<OrderPresenter> implements OrderView{

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    List<String> mTitleList = new ArrayList<>();;
    List<Fragment> mFragmentList = new ArrayList<>();
    BaseFragmentPagerAdapter mAdapter;

    @Override
    public int getViewId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        getToolbar().setTitle("我的订单");
        mTitleList.add("待付款");
        mTitleList.add("待发货");
        mTitleList.add("待收货");
        mTitleList.add("已完成");
        mFragmentList.add(OrderListFragment.newInstance(1));
        mFragmentList.add(OrderListFragment.newInstance(2));
        mFragmentList.add(OrderListFragment.newInstance(3));
        mFragmentList.add(OrderListFragment.newInstance(4));
        mAdapter.setFragment(mTitleList, mFragmentList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        int item = getArguments().getInt(Constants.INTENT_KEY1, 0);
        mViewPager.setCurrentItem(item, false);

    }

    @Override
    public OrderPresenter createPresenter() {
        return new OrderPresenter(new OrderModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
