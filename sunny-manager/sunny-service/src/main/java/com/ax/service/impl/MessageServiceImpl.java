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

        List<Byte> status = new ArrayList();
        //添加状态  1 表示未阅读  2 表示以阅读
        status.add((byte) 1);
        status.add((byte) 2);

        TbMessageExample tme = new TbMessageExample();
        TbMessageExample.Criteria criteria = tme.createCriteria();
        criteria.andSendIdIn(ids);
        criteria.andReIdIn(ids);
        criteria.andGoodsIdEqualTo(goodsId);
        criteria.andStatusIn(status);

        List<TbMessage> list = mapper.selectByExample(tme);  //查询结果

        Comparator comparatorUtils = new ComparatorUtils();  //创建排序器

        list.sort(comparatorUtils);

        return list;
    }

    @Override
    public List<TbMessage> findNewMessage(Long reId) {

        //注意：该方法内部存在  状态限制  如果 状态发生改变，记得修改这里面的状态
        List<TbMessage> newMessage = mapper.findNewMessage(reId);

        return newMessage;
    }

    /**
     * 修改消息为已阅读状态
     */
    @Override
    public void updateMessageStatusForReaded(List<TbMessage> message, Long id) {

        //转换一下  接收者id
        long reId = id;

        //从message中，挑出接收者为 id 的消息
        if (message != null) {

            //保存接收者 为 查询者  的消息id
            List<Long> messageIds = new ArrayList();

            for (TbMessage tbMessage : message) {
                if (tbMessage.getReId() == reId) {
                    messageIds.add(tbMessage.getId());
                }
            }
            if (messageIds.size() > 0) {
                //修改条件
                TbMessageExample me = new TbMessageExample();
                me.createCriteria().andIdIn(messageIds);

                TbMessage m = new TbMessage();
                m.setStatus((byte) 2);
                mapper.updateByExampleSelective(m, me);
            }
        }
    }

    /**
     * 查询指定用户是否存在 未阅读消息  //TODO  此处代码未完成
     */
    @Override
    public boolean findUnreadMessage(Long userId) {
        //如何实 查询未读 消息

        TbMessageExample me = new TbMessageExample();

        TbMessageExample.Criteria criteria = me.createCriteria();

        criteria.andReIdEqualTo(userId);
        //状态为 1  表示
        criteria.andStatusEqualTo((byte) 1);

        List<TbMessage> list = mapper.selectByExample(me);

        if (list != null && list.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
