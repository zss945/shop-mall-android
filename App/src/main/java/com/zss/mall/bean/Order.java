package com.zss.mall.bean;

import java.util.Date;
import java.util.List;

public class Order {

    public Long id;
    public String sn;
    public String consignee;
    public String areaName;
    public String address;
    public String phone;
    public String amount;
    public String offsetAmount;
    public Date createTime;
    public Date expire;
    public String exchangePoint;
    public String freight;
    public String invoiceTitle;
    public String invoiceContent;
    public Integer quantity;
    public String memo;
    public Integer status;
    public Long memberId;
    public List<OrderItem> list;

}
