package com.ax.pojogroup;

import com.ax.pojo.TbContent;
import com.ax.pojo.TbGoods;
import com.ax.pojo.TbImage;
import com.ax.pojo.TbType;

import java.util.List;

/**
 *  商品信息
 * */
public class NewGoods {

    private TbGoods goods;

    private TbType type;

    private TbContent content;

    private List<TbImage> imageList;

    public TbGoods getGoods() {
        return goods;
    }

    public void setGoods(TbGoods goods) {
        this.goods = goods;
    }

    public TbType getType() {
        return type;
    }

    public void setType(TbType type) {
        this.type = type;
    }

    public TbContent getContent() {
        return content;
    }

    public void setContent(TbContent content) {
        this.content = content;
    }

    public List<TbImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<TbImage> imageList) {
        this.imageList = imageList;
    }
}
