package com.zss.mall.fragment.goods;

import android.util.Log;

import com.zss.kit.FastjsonUtils;
import com.zss.mall.bean.GoodsCategory;
import com.zss.mall.bean.ResultBean;
import com.zss.mall.network.BaseResponse;
import com.zss.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GoodsPresenter extends BasePresenter<GoodsModel, GoodsView> {

    GoodsPresenter(GoodsModel mModel, GoodsView mView) {
        super(mModel, mView);
    }

    void queryTreeCategory() {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", "1");
        getModel().getTreeCategory(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String jsonList = data.optString("list");
                List<GoodsCategory> categoryList = FastjsonUtils.toList(jsonList, GoodsCategory.class);
                getView().setCategoryTitle(categoryList);
            }
            @Override
            public void onError(String errMsg) {

            }
        });
    }
}