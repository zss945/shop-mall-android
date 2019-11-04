package com.zss.mall.activity.select_area;

import com.zss.mall.bean.Area;
import com.zss.ui.mvp.base.BaseView;

import java.util.List;

public interface SelectAreaView extends BaseView {

    void setLevelAreaList(List<Area> areaList);

    void setParentAreaList(List<Area> areaList);

}
