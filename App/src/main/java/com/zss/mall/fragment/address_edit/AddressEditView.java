package com.zss.mall.fragment.address_edit;

import com.zss.mall.bean.Area;
import com.zss.ui.mvp.base.BaseView;

import java.util.List;

public interface AddressEditView extends BaseView {

    void saveUpdateSuccess();

    void setSelectAreaList(List<Area> areaList);

}
