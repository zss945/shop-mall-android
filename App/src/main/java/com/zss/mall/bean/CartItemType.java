package com.zss.mall.bean;

import com.zss.mall.room.entity.Cart;

public class CartItemType {

    private Cart cart; //购物车

    private ItemType itemType; //列表类型

    public enum ItemType {

        ItemTypeEmpty(1), //空列表
        ItemTypeCart(2);  //购物车

        int value;

        ItemType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public CartItemType(Cart cart) {
        this.cart = cart;
        this.itemType = ItemType.ItemTypeCart;
    }

    public CartItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
