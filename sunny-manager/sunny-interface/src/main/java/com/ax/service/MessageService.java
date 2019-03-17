package com.ax.service;

import com.ax.entity.Result;
import com.ax.pojo.TbMessage;
import com.ax.pojo.TbUser; /**
 *  消息接口
 * */
public interface MessageService {


//    增加
    Result add(TbUser user, TbMessage message) throws Exception;
//    查询
    Result get(TbUser user);
}
