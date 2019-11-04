package com.zss.mall.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tb_cart")
public class Cart {

    @PrimaryKey
    @ColumnInfo(name = "cart_item_id")
    private Long cartItemId;

    @ColumnInfo(name = "product_id")
    private Long productId;

    @ColumnInfo(name = "quantity")
    private Integer quantity;

    @ColumnInfo(name = "cart_key")
    private String cartKey;

    @ColumnInfo(name = "goods_id")
    private Long goodsId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "specification_values")
    private String specificationValues;

    @ColumnInfo(name = "is_select")
    private Integer isSelect = 1;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCartKey() {
        return cartKey;
    }

    public void setCartKey(String cartKey) {
        this.cartKey = cartKey;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSpecificationValues() {
        return specificationValues;
    }

    public void setSpecificationValues(String specificationValues) {
        this.specificationValues = specificationValues;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

}
