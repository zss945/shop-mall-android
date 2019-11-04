package com.zss.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zss.ui.R;
import com.zss.ui.activity.base.BaseActivity;
import com.zss.ui.mvp.IPresenter;

/**
 * BaseFragmentActivity中，默认添加Fragment管理。
 */
public class BaseFragmentActivity extends BaseActivity {

    private static String FGT_NAME = "fgt_name";

    public static void createFragment(Context context, Class cls) {
        createFragment(context, cls, null);
    }

    public static void createFragment(Context context, Class cls, Bundle args) {
        Context mContext = context.getApplicationContext();
        Intent intent = new Intent(mContext, BaseFragmentActivity.class);
        intent.putExtra(FGT_NAME, cls);
        if (args != null) {
            intent.putExtras(args);
        }
        mContext.startActivity(intent);
    }

    public static void createFragmentNewTask(Context context, Class cls, Bundle args) {
        Context mContext = context.getApplicationContext();
        Intent intent = new Intent(mContext, BaseFragmentActivity.class);
        intent.putExtra(FGT_NAME, cls);
        if (args != null) {
            intent.putExtras(args);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public int getViewId() {
        return R.layout.activity_base;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        Class fgtName = (Class) intent.getSerializableExtra(FGT_NAME);
        Fragment fragment = Fragment.instantiate(getBaseContext(), fgtName.getName(), getIntent().getExtras());
        addFragment(fragment);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @Override
    public void onBackPressed() {
        Fragment curBase = getCurrentFragment();
        if (curBase != null) {
            FragmentManager fm = getSupportFragmentManager();
            int count = fm.getBackStackEntryCount();
            if (count > 1) {
                backStackFragment();
            } else {
                finish();
            }
        }
    }

    /**
     * 添加Fragment
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        if (fragment == null)
            return;
        String tag = fragment.getClass().getName();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment tempFragment = fm.findFragmentByTag(tag);
        if (tempFragment != null) {
            ft.replace(R.id.content_view, fragment, tag);
        } else {
            ft.add(R.id.content_view, fragment, tag);
        }
        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }

    /**
     * 返回前一堆栈Fragment
     */
    public void backStackFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate();
    }


    /**
     * 返回className指定堆栈Fragment，className本身也会销毁
     *
     * @param className
     */
    public void backStackFragment(String className) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate(className, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * 获取当前Fragment
     *
     * @return
     */
    public Fragment getCurrentFragment() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(count - 1);
            String name = entry.getName();
            return getFragment(name);
        } else {
            return null;
        }
    }

    /**
     * 通过className获取Fragment
     *
     * @param className
     * @return
     */
    public Fragment getFragment(String className) {
        return getSupportFragmentManager().findFragmentByTag(className);
    }

}
