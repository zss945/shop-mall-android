package com.zss.mall.fragment.select_pay;
import com.alipay.sdk.app.PayTask;
import com.zss.mall.BaseApplication;
import com.zss.mall.bean.ResultBean;
import com.zss.mall.network.BaseResponse;
import com.zss.ui.mvp.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

public class SelectPayPresenter extends BasePresenter<SelectPayModel, SelectPayView> {

    public SelectPayPresenter(SelectPayModel mModel, SelectPayView mView) {
        super(mModel, mView);
    }

    void alipay(Long orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        getModel().alipay(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                String result = bean.getData();
                getView().alipayResult(result);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
