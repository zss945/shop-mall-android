package com.zss.mall.fragment.cart;

import com.zss.mall.bean.Goods;
import com.zss.mall.room.entity.Cart;
import com.zss.ui.mvp.base.BaseView;

import java.util.List;

public interface CartView extends BaseView {

    void setCartList(List<Cart> cartList);

    void setGoodsList(List<Goods> goodsList);
}
