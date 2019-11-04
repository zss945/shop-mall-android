package com.zss.mall.fragment.login;

import android.util.Log;

import com.zss.kit.FastjsonUtils;
import com.zss.mall.BaseApplication;
import com.zss.mall.bean.CartItemProduct;
import com.zss.mall.bean.Event;
import com.zss.mall.bean.Member;
import com.zss.mall.bean.ResultBean;
import com.zss.mall.kit.AppUtils;
import com.zss.mall.network.BaseResponse;
import com.zss.mall.room.entity.Cart;
import com.zss.ui.mvp.base.BasePresenter;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

class LoginPresenter extends BasePresenter<LoginModel, LoginView> {

    LoginPresenter(LoginModel mModel, LoginView mView) {
        super(mModel, mView);
    }

    void login(String mobile, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("password", password);
        getModel().login(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String expire = data.optString("expire");
                String token = data.optString("token");
                JSONObject jsonMember = data.optJSONObject("member");
                String memberId = jsonMember.optString("id");
                String mobile = jsonMember.optString("mobile");
                String sn = jsonMember.optString("sn");
                Member member = new Member();
                member.expire = expire;
                member.token = token;
                member.memberId = memberId;
                member.mobile = mobile;
                member.sn = sn;
                AppUtils.saveMember(member);

                //发射登录事件
                EventBus.getDefault().post(new Event.LoginEvent());

                JSONObject jsonCart = jsonMember.optJSONObject("cart");
                if (jsonCart != null) {
                    String cartKey = jsonCart.optString("cartKey");
                    String jsonList = jsonCart.optString("list");
                    List<CartItemProduct> cartItemProductList = FastjsonUtils.toList(jsonList, CartItemProduct.class);
                    if (cartItemProductList != null) {
                        List<Cart> cartList = new ArrayList<>();
                        for (CartItemProduct item : cartItemProductList) {
                            Cart cart = new Cart();
                            cart.setCartItemId(item.id);
                            cart.setProductId(item.productId);
                            cart.setQuantity(item.quantity);
                            cart.setCartKey(cartKey);
                            cart.setName(item.name);
                            cart.setImage(item.image);
                            cart.setPrice(item.price);
                            cart.setSpecificationValues(item.specificationValues);
                            cartList.add(cart);
                        }
                        saveDB(cartList);
                    } else {
                        getView().goHome();
                    }
                } else {
                    getView().goHome();
                }
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    private void saveDB(List<Cart> cartList) {
        Observable.empty()
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        BaseApplication.getInstance().getCartDao().addOrReplaceList(cartList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        EventBus.getDefault().post(new Event.CartEvent());
                        getView().goHome();
                    }
                })
                .subscribe();
    }

}
