package com.zss.mall.fragment.cart;

import com.zss.kit.FastjsonUtils;
import com.zss.mall.bean.Goods;
import com.zss.mall.bean.ResultBean;
import com.zss.mall.network.BaseResponse;
import com.zss.mall.room.entity.Cart;
import com.zss.ui.mvp.base.BasePresenter;

import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Consumer;

class CartPresenter extends BasePresenter<CartModel, CartView> {

    CartPresenter(CartModel mModel, CartView mView) {
        super(mModel, mView);
    }

    void queryGoodsByLike(long page, long limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        getModel().queryGoodsByLike(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                JSONObject page = data.optJSONObject("page");
                String jsonList = page.optString("list");
                List<Goods> goodsList = FastjsonUtils.toList(jsonList, Goods.class);
                getView().setGoodsList(goodsList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    void queryAllCart() {
        getModel().queryAllCart(new Consumer<List<Cart>>() {
            @Override
            public void accept(List<Cart> cartList) throws Exception {
                getView().setCartList(cartList);
            }
        });
    }
}
