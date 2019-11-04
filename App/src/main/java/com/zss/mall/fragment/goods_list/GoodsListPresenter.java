package com.zss.mall.fragment.goods_list;

import com.zss.kit.FastjsonUtils;
import com.zss.mall.bean.Goods;
import com.zss.mall.bean.ResultBean;
import com.zss.mall.network.BaseResponse;
import com.zss.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GoodsListPresenter extends BasePresenter<GoodsListModel, GoodsListView> {

    GoodsListPresenter(GoodsListModel mModel, GoodsListView mView) {
        super(mModel, mView);
    }

    void queryGoodsByCategory(Long categoryId, int level, int page, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        params.put("level", level);
        params.put("page", page);
        params.put("limit", limit);
        getModel().queryGoodsByCategory(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                JSONObject page = data.optJSONObject("page");
                String jsonList = page.optString("list");
                long currPage = page.optLong("currPage");
                long totalPage = page.optLong("totalPage");
                List<Goods> goodsList = FastjsonUtils.toList(jsonList, Goods.class);
                getView().setGoodsList(currPage, totalPage, goodsList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
