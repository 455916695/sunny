package com.ax.pojogroup;

import com.ax.pojo.TbType;

import java.util.List;

public class Type {

    private TbType tbType;

    private List<TbType> tbTypeList;

    public TbType getTbType() {
        return tbType;
    }

    public void setTbType(TbType tbType) {
        this.tbType = tbType;
    }

    public List<TbType> getTbTypeList() {
        return tbTypeList;
    }

    public void setTbTypeList(List<TbType> tbTypeList) {
        this.tbTypeList = tbTypeList;
    }
}
