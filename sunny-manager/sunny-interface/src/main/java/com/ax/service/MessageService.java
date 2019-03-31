package com.ax.service;

import com.ax.entity.Result;
import com.ax.pojo.TbMessage;
import com.ax.pojo.TbUser;

import java.util.List;

/**
 * 消息接口
 */
public interface MessageService {


    //    增加
    Result add(TbUser user, TbMessage message) throws Exception;

    //    查询
    Result get(TbUser user);

    /**
     * 发送消息
     */
    public void addMessage(TbMessage tbMessage);

    /**
     * 查看消息
     */
    public List<TbMessage> findMessage(Long id, Long otherId, Long goodsId);

    /**
     * 查看最新消息
     */
    public List<TbMessage> findNewMessage(Long reId);

}
