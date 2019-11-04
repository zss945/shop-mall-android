package com.zss.mall.fragment.address;

import com.zss.mall.bean.Address;
import com.zss.ui.mvp.base.BaseView;

import java.util.List;

public interface AddressView extends BaseView {

    void setAddressList(List<Address> addressList);
}
