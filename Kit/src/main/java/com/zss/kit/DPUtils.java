package com.zss.kit;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 公用UI工具类
 *
 * @author zm
 */
public class DPUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Resources resources, float dpValue) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, resources.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Resources resources, float pxValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
     */
    public static int px2sp(Resources resources, float pxValue) {
        float fontScale = resources.getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px
     */
    public static int sp2px(Resources resources, float spValue) {
        float fontScale = resources.getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenW(Resources resources) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenH(Resources resources) {
        DisplayMetrics dm = resources.getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }

    /**
     * 获取屏幕高度
     */
    public static final int getHeightInDP(Resources resources) {
        final float height = resources.getDisplayMetrics().heightPixels;
        return px2dp(resources, height);
    }

    /**
     * 获取屏幕宽度
     */
    public static final int getWidthInDP(Resources resources) {
        final float width = resources.getDisplayMetrics().widthPixels;
        return px2dp(resources, width);
    }


}
