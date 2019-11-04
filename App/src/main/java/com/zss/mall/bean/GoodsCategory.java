package com.zss.mall.bean;

import java.util.List;

/**
 * 商品目录
 * @author zm
 */
public class GoodsCategory {

    public Long id; //商品目录

    public String name; //目录名称

    public Long parentId; //父节点ID

    public String image; //目录图片

    public Integer level; //级别

    public List<GoodsCategory> list; //子目录

}
