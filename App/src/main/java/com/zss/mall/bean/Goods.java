package com.zss.mall.bean;

import com.zss.kit.FastjsonUtils;

import java.util.List;

public class Goods {

    public Long id; //商品ID

    public String name; //商品名称

    public String caption; //商品简介

    public String price; //商品价格

    public String image; //商品图片

    public String goodsImages; //商品展示图片

    public String parameterValues; //商品参数

    public String specificationItems; //商品规格

    public List<SpecificationItem> specificationItemList;


    public boolean hasSpecification() {
        if (this.specificationItemList == null) {
            this.specificationItemList = FastjsonUtils.toList(specificationItems, SpecificationItem.class);
        }
        return this.specificationItemList != null && !this.specificationItemList.isEmpty();
    }

}
