package com.zss.mall.fragment.address;

import com.zss.kit.FastjsonUtils;
import com.zss.mall.bean.Address;
import com.zss.mall.bean.ResultBean;
import com.zss.mall.network.BaseResponse;
import com.zss.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.List;

public class AddressPresenter extends BasePresenter<AddressModel, AddressView> {

    public AddressPresenter(AddressModel mModel, AddressView mView) {
        super(mModel, mView);
    }

    void queryAddressList() {
        getModel().queryAddressList(new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String jsonList = data.optString("list");
                List<Address> addressList = FastjsonUtils.toList(jsonList, Address.class);
                getView().setAddressList(addressList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

}
