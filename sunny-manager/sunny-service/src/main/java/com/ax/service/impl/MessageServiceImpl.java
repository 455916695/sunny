package com.ax.service.impl;

import com.ax.entity.ComparatorUtils;
import com.ax.entity.Result;
import com.ax.mapper.TbMessageMapper;
import com.ax.pojo.TbMessage;
import com.ax.pojo.TbMessageExample;
import com.ax.pojo.TbUser;
import com.ax.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private TbMessageMapper mapper;

    @Override
    public Result add(TbUser user, TbMessage message) throws Exception {
        message.setSendId(user.getId());
        message.setUpdateTime(new Date());
        mapper.insert(message);
        return new Result(true, "消息发送成功", message);
    }

    @Override
    public Result get(TbUser user) {
        //获得所有信息按时间排序
        TbMessageExample example = new TbMessageExample();
        TbMessageExample.Criteria criteria = example.createCriteria();
        criteria.andSendIdEqualTo(user.getId()).andReIdEqualTo(user.getId());
        List<TbMessage> list = mapper.selectByExample(example);
        //未经过排序
        //比对信息确定发送者 交由前台比对
        return null;
    }
//=======我是神奇的分界线=================================================================================


    /**
     * 添加消息
     */
    @Override
    public void addMessage(TbMessage tbMessage) {
//        private Byte status;
//        private Date createTime;
//        private Date updateTime;
        tbMessage.setStatus((byte) 1);
        tbMessage.setCreateTime(new Date());
        tbMessage.setUpdateTime(new Date());
        mapper.insert(tbMessage);
    }

    @Override
    public List<TbMessage> findMessage(Long id, Long otherId, Long goodsId) {

        List<Long> ids = new ArrayList();   //创建 id 集合 ，包含 双方id
        ids.add(id);
        ids.add(otherId);
        //直接自己写sql

        TbMessageExample tme = new TbMessageExample();
        TbMessageExample.Criteria criteria = tme.createCriteria();
        criteria.andSendIdIn(ids);
        criteria.andReIdIn(ids);
        criteria.andGoodsIdEqualTo(goodsId);

        List<TbMessage> list = mapper.selectByExample(tme);  //查询结果

        Comparator comparatorUtils = new ComparatorUtils();  //创建排序器

        list.sort(comparatorUtils);

        return list;
    }

    @Override
    public List<TbMessage> findNewMessage(Long reId) {

        List<TbMessage> newMessage = mapper.findNewMessage(reId);

        return newMessage;
    }
}
