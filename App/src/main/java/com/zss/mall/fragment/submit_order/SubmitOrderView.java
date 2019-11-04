package com.zss.mall.fragment.submit_order;

import com.zss.mall.bean.Address;
import com.zss.mall.bean.Order;
import com.zss.ui.mvp.base.BaseView;

public interface SubmitOrderView extends BaseView {

    void setAddress(Address address);

    void goPay(Order order);

}
