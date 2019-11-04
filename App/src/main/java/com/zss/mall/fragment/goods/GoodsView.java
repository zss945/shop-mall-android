package com.zss.mall.fragment.goods;

import com.zss.mall.bean.GoodsCategory;
import com.zss.ui.mvp.base.BaseView;

import java.util.List;

public interface GoodsView extends BaseView {

    void setCategoryTitle(List<GoodsCategory> list);

}
