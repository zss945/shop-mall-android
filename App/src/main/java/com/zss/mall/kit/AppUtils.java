package com.zss.mall.kit;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.zss.kit.BigDecimalUtils;
import com.zss.kit.FastjsonUtils;
import com.zss.kit.SPUtils;
import com.zss.mall.BaseApplication;
import com.zss.mall.Constants;
import com.zss.mall.bean.Event;
import com.zss.mall.bean.Member;
import com.zss.mall.bean.SpecificationItem;
import com.zss.mall.bean.SpecificationValue;
import com.zss.mall.network.GlideApp;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AppUtils {

    public static String fromRMB(String text) {
        BigDecimal decimal = new BigDecimal(10);
        String value = decimal.pow(4).toPlainString();
        return BigDecimalUtils.mul(text, value);
    }

    public static String toRMB(String text) {
        BigDecimal decimal = new BigDecimal(10);
        String value = decimal.pow(4).toPlainString();
        return BigDecimalUtils.div(text, value, 2);
    }

    public static String toRMBFormat(String text) {
        return "￥" + toRMB(text);
    }

    public static void saveMember(Member member) {
        SPUtils.putJSONCache(BaseApplication.getInstance(), Constants.SP_USER_INFO, FastjsonUtils.toString(member));
    }

    public static Member getMember() {
        String json = SPUtils.getJSONCache(BaseApplication.getInstance(), Constants.SP_USER_INFO);
        if (!TextUtils.isEmpty(json)) {
            return FastjsonUtils.toObject(json, Member.class);
        } else {
            return null;
        }
    }

    public static boolean isLogin() {
        return getMember() != null;
    }

    public static String formatPhone(String phone) {
        int position = phone.indexOf(")");
        if (position >= 0) {
            phone = phone.substring(position);
        }
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    public static String getSelectSpecValue(String specificationValue) {
        if (specificationValue == null) return "";
        List<SpecificationValue> specValueList = FastjsonUtils.toList(specificationValue, SpecificationValue.class);
        StringBuilder specValues = new StringBuilder();
        assert specValueList != null;
        for (SpecificationValue item : specValueList) {
            specValues.append(",");
            specValues.append(item.value);
        }
        return specValues.substring(1);
    }

    public static String getSelectSpecItem(String specificationItem) {
        if (specificationItem == null) return "";
        List<SpecificationItem> specValueList = FastjsonUtils.toList(specificationItem, SpecificationItem.class);
        StringBuilder specValues = new StringBuilder();
        assert specValueList != null;
        for (SpecificationItem item : specValueList) {
            if (item.options != null && item.options.size() > 0) {
                specValues.append(",");
                specValues.append(item.name);
            }
        }
        return specValues.substring(1);
    }

    public static boolean isValidateMobile(String phone) {
        String regex = "1[34578]([0-9]){9}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static void exitLogin() {
        Observable.empty()
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        BaseApplication.getInstance().getCartDao().deleteAll();
                        SPUtils.putJSONCache(BaseApplication.getInstance(), Constants.SP_USER_INFO, "");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        EventBus.getDefault().post(new Event.LoginEvent());
                        EventBus.getDefault().post(new Event.CartEvent());
                    }
                })
                .subscribe();
    }

    public static int getColor(int colorId) {
        return ContextCompat.getColor(BaseApplication.getInstance(), colorId);
    }

    public static void loadImage(String url, ImageView image){
        GlideApp.with(BaseApplication.getInstance()).load(Constants.getImageUrl() + url).centerCrop().into(image);
    }

}
