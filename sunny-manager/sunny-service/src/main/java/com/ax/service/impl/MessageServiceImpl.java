package com.ax.service.impl;

import com.ax.entity.Result;
import com.ax.mapper.TbMessageMapper;
import com.ax.pojo.TbMessage;
import com.ax.pojo.TbMessageExample;
import com.ax.pojo.TbUser;
import com.ax.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private TbMessageMapper mapper;
    @Override
    public Result add(TbUser user, TbMessage message) throws Exception{
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
}
