package com.ax.pojo;

import java.util.Date;

public class Goods {
    private String uuid;

    private String description;

    private String desimg;

    private Integer price;

    private Integer cutprice;

    private Date createtime;

    private Date updatetime;

    private Integer status;

    private Integer category;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getDesimg() {
        return desimg;
    }

    public void setDesimg(String desimg) {
        this.desimg = desimg == null ? null : desimg.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCutprice() {
        return cutprice;
    }

    public void setCutprice(Integer cutprice) {
        this.cutprice = cutprice;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}