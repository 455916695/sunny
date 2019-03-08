package com.ax.pojo;

import java.util.Date;

public class TbImage {
    private Long id;

    private Integer kind;  //图片种类 1 用户头像  2 商品图片

    private Long kindId; //来源id   （1用户id 或 2商品id）

    private String address;

    private Integer status; //状态  1 正常  2 删除

    private Date createTime;

    private Date updateTime;

    public TbImage() {
    }

    public TbImage(Long id, Integer kind, Long kindId, String address) {
        this.id = id;
        this.kind = kind;
        this.kindId = kindId;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public Long getKindId() {
        return kindId;
    }

    public void setKindId(Long kindId) {
        this.kindId = kindId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}