package com.ax.pojogroup;

import com.ax.entity.Goods;
import com.ax.pojo.TbOrder;
import com.ax.pojo.TbUser;


public class Order {

    private TbOrder order;  //订单本身
    private TbUser buyer;   //买家       查询订单详情的时候可能需要展示
    private TbUser seller;  //卖家       查询订单详情的时候可能需要展示
    private NewGoods goods;  //商品

    public TbOrder getOrder() {
        return order;
    }

    public void setOrder(TbOrder order) {
        this.order = order;
    }

    public TbUser getBuyer() {
        return buyer;
    }

    public void setBuyer(TbUser buyer) {
        this.buyer = buyer;
    }

    public TbUser getSeller() {
        return seller;
    }

    public void setSeller(TbUser seller) {
        this.seller = seller;
    }

    public NewGoods getGoods() {
        return goods;
    }

    public void setGoods(NewGoods goods) {
        this.goods = goods;
    }
}
