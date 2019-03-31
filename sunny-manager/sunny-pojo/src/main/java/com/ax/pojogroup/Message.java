package com.ax.pojogroup;

import com.ax.pojo.TbImage;
import com.ax.pojo.TbMessage;

public class Message {

    private TbMessage message;  //消息实体
    private User reUser;        //接收者信息
    private User sendUser;      //发送者信息
    private TbImage image;  // 商品图片

    public TbMessage getMessage() {
        return message;
    }

    public void setMessage(TbMessage message) {
        this.message = message;
    }

    public User getReUser() {
        return reUser;
    }

    public void setReUser(User reUser) {
        this.reUser = reUser;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public TbImage getImage() {
        return image;
    }

    public void setImage(TbImage image) {
        this.image = image;
    }
}
