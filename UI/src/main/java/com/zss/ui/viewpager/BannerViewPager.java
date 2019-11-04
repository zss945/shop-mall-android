package com.zss.ui.viewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.tmall.ultraviewpager.UltraViewPager;
import com.zss.kit.DPUtils;
import com.zss.ui.R;
import com.zss.ui.adapter.viewpager.BaseBannerPagerAdapter;

public class BannerViewPager extends FrameLayout {

    private UltraViewPager mViewPager;

    public BannerViewPager(Context context) {
        super(context);
        initView();
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAtrr) {
        super(context, attrs, defStyleAtrr);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.viewpager_banner, this);
        mViewPager = findViewById(R.id.ultra_viewpager);
    }

    public UltraViewPager getViewPager() {
        return mViewPager;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPager.setOnPageChangeListener(listener);
    }

    public void setAdapter(BaseBannerPagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    public void setDefaultIndicator() {
        if (mViewPager.getAdapter().getCount() > 1) {
            int space = DPUtils.dp2px(getResources(), 10);
            int radius = DPUtils.dp2px(getResources(), 4);
            mViewPager.initIndicator();
            mViewPager.getIndicator().setFocusColor(Color.LTGRAY);
            mViewPager.getIndicator().setNormalColor(Color.WHITE);
            mViewPager.getIndicator().setRadius(radius);
            mViewPager.getIndicator().setMargin(0, 0, 0, space);
            mViewPager.getIndicator().setIndicatorPadding(space);
            mViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            mViewPager.getIndicator().setOrientation(UltraViewPager.Orientation.HORIZONTAL);
            mViewPager.getIndicator().build();
            mViewPager.setInfiniteLoop(true);
            mViewPager.setHGap(0);
        } else {
            mViewPager.setInfiniteLoop(false);
            mViewPager.disableIndicator();
            mViewPager.disableAutoScroll();
        }
    }

    public int getCount() {
        return mViewPager.getAdapter().getCount();
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }
}