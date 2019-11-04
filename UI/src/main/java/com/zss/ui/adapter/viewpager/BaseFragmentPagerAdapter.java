package com.zss.ui.adapter.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 公用FragmentPagerAdapter适配器
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> mTitleList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager manager) {
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
