package com.zss.mall.activity.select_spec;

import com.zss.mall.bean.Product;
import com.zss.ui.mvp.base.BaseView;


public interface SelectSpecView extends BaseView {

    void setProduct(Product product);

    void goBack();

}
