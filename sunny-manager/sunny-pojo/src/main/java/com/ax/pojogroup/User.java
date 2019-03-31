package com.ax.pojogroup;

import com.ax.pojo.TbImage;
import com.ax.pojo.TbUser;

public class User {


    private TbUser user;

    private TbImage image;

    public TbUser getUser() {
        return user;
    }

    public void setUser(TbUser user) {
        this.user = user;
    }

    public TbImage getImage() {
        return image;
    }

    public void setImage(TbImage image) {
        this.image = image;
    }
}
