package com.ax.entity;

import com.ax.pojo.TbComment;
import com.ax.pojo.TbContent;

import java.util.Date;

public class Comment extends TbComment {
    private Long id;
    private Long buyerId;
    private Long sellerId;
    private Long goodsId;
    private Long contentId;
    private Date createTime;
    private Date updateTime;

    private User buyer;
    private User seller;
    private Goods goods;
    private TbContent tbContent;

    public Comment() {
    }
    public Comment(TbComment comment) {
        this.id = comment.getId();
        this.buyerId = comment.getBuyerId();
        this.sellerId = comment.getSellerId();
        this.goodsId = comment.getGoodsId();
        this.contentId = comment.getContentId();
        this.createTime = comment.getCreateTime();
        this.updateTime = comment.getUpdateTime();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getBuyerId() {
        return buyerId;
    }

    @Override
    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public Long getSellerId() {
        return sellerId;
    }

    @Override
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public Long getGoodsId() {
        return goodsId;
    }

    @Override
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public Long getContentId() {
        return contentId;
    }

    @Override
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public TbContent getTbContent() {
        return tbContent;
    }

    public void setTbContent(TbContent tbContent) {
        this.tbContent = tbContent;
    }
}
