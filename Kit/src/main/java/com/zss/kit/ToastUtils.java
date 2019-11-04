package com.zss.kit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static Toast mToast;

    @SuppressLint("ShowToast")
    public static void init(Context context) {
        mContext = context.getApplicationContext();
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }

    public static void showToast(String text) {
        assert mToast != null;
        mToast.setText(text);
        mToast.show();
    }


    public static void showToast(int resId) {
        assert mToast != null;
        mToast.setText(resId);
        mToast.show();
    }

}
