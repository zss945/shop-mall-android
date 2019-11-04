package com.zss.ui.adapter.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用FragmentStatePagerAdapter适配器
 */
public class BaseFragmentPagerStateAdapter extends FragmentStatePagerAdapter {

    public List<String> mTitleList = new ArrayList<>();
    public List<Fragment> mFragmentList = new ArrayList<>();

    public BaseFragmentPagerStateAdapter(FragmentManager manager) {
            super(manager);
        }

    @Override
    public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

    @Override
    public int getCount() {
            return mFragmentList.size();
        }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitleList.size() >= position){
            return mTitleList.get(position);
        }else{
            return super.getPageTitle(position);
        }
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragment(String title, Fragment fragment) {
        mTitleList.add(title);
        mFragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void removeFragment(Fragment fragment) {
        mFragmentList.remove(fragment);
        notifyDataSetChanged();
    }

    public void removeFragment(String title, Fragment fragment) {
        mTitleList.remove(title);
        mFragmentList.remove(fragment);
        notifyDataSetChanged();
    }

    public void setFragment(List<Fragment> mFragmentList) {
        this.mFragmentList = mFragmentList;
        notifyDataSetChanged();
    }

    public void setFragment(List<String> mTitleList, List<Fragment> mFragmentList) {
        this.mTitleList = mTitleList;
        this.mFragmentList = mFragmentList;
        notifyDataSetChanged();
    }

    public void addAllFragment(List<Fragment> mFragmentList) {
        this.mFragmentList.addAll(mFragmentList);
    }

    public void addAllFragment(List<String> mTitleList, List<Fragment> mFragmentList) {
        this.mTitleList.addAll(mTitleList);
        this.mFragmentList.addAll(mFragmentList);
    }

    public List<Fragment> getFragmentList(){
        return mFragmentList;
    }

    public List<String> getTitleList(){
        return mTitleList;
    }

}
