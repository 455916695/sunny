package com.ax.entity;

import com.ax.pojo.TbGoods;
import com.ax.pojo.TbImage;

import java.util.Date;
import java.util.List;

public class Goods extends TbGoods {

    private Long id;

    private String name;

    private Long sellerId;

    private Long contentId;

    private Byte typeId;

    private Integer number;

    private Integer price;

    private Byte oldDegree;

    private Byte means;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String type; //类型

    private String content; //描述

    private List<TbImage> imageList;  //图片

    public Goods() {
    }


    public Goods(TbGoods tbGoods) {
        this.id = tbGoods.getId();
        this.name = tbGoods.getName();
        this.sellerId = tbGoods.getSellerId();
        this.contentId = tbGoods.getContentId();
        this.typeId = tbGoods.getTypeId();
        this.number = tbGoods.getNumber();
        this.price = tbGoods.getPrice();
        this.oldDegree = tbGoods.getOldDegree();
        this.means = tbGoods.getMeans();
        this.status = tbGoods.getStatus();
        this.createTime = tbGoods.getCreateTime();
        this.updateTime = tbGoods.getUpdateTime();
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public Long getContentId() {
        return contentId;
    }

    @Override
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    @Override
    public Byte getTypeId() {
        return typeId;
    }

    @Override
    public void setTypeId(Byte typeId) {
        this.typeId = typeId;
    }

    @Override
    public Integer getNumber() {
        return number;
    }

    @Override
    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public Integer getPrice() {
        return price;
    }

    @Override
    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public Byte getOldDegree() {
        return oldDegree;
    }

    @Override
    public void setOldDegree(Byte oldDegree) {
        this.oldDegree = oldDegree;
    }

    @Override
    public Byte getMeans() {
        return means;
    }

    @Override
    public void setMeans(Byte means) {
        this.means = means;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<TbImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<TbImage> imageList) {
        this.imageList = imageList;
    }
}
