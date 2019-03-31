package com.ax.entity;

import com.ax.pojo.TbMessage;

import java.util.Comparator;

/**
 * 消息对象根据时间进行排序
 */
public class ComparatorUtils implements Comparator {

    public int compare(Object o1, Object o2) {
        //此处没有考虑一些，情况
        if (o1 == null || o2 == null) {
            return 0;
        }
        TbMessage m1 = null;
        TbMessage m2 = null;
        if (o1 instanceof TbMessage) {
            m1 = (TbMessage) o1;
        }
        if (o1 instanceof TbMessage) {
            m2 = (TbMessage) o2;
        }

        if (m1.getCreateTime() == null || m2.getCreateTime() == null)
            return 0;

        return m1.getCreateTime().compareTo(m2.getCreateTime());
    }

}
