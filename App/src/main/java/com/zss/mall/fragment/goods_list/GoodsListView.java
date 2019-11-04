package com.zss.mall.fragment.goods_list;

import com.zss.mall.bean.Goods;
import com.zss.ui.mvp.base.BaseView;

import java.util.List;

public interface GoodsListView extends BaseView {

    void setGoodsList(long currPage, long totalPage, List<Goods> goodsList);

}
