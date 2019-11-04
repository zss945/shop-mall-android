package com.zss.mall.fragment.order_list;

import com.zss.mall.bean.Order;
import com.zss.ui.mvp.base.BaseView;

import java.util.List;

public interface OrderListView extends BaseView {

    void setOrderList(long currPage, long totalPage, List<Order> orderList);
}
