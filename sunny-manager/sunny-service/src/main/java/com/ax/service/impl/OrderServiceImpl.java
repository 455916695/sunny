package com.ax.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.mapper.TbOrderMapper;
import com.ax.pojo.TbImageExample;
import com.ax.pojo.TbOrder;
import com.ax.pojo.TbOrderExample;
import com.ax.pojo.TbUser;
import com.ax.service.OrderService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    private static final Byte DEFAULT_STATUS_NOPAY = 1; // 1 未支付  2 已支付  3 确认收货  4 完成订单
    private static final Byte DEFAULT_STATUS_PAY = 2; //   1 未支付  2 已支付  3 确认收货  4 完成订单
    private static final Byte DEFAULT_STATUS_GETED = 3; // 1 未支付  2 已支付  3 确认收货  4 完成订单
    private static final Byte DEFAULT_STATUS_FINSHED = 4; //1 未支付  2 已支付  3 确认收货  4 完成订单

    /**
     * 查询全部
     */
    @Override
    public List<TbOrder> findAll() {
        return orderMapper.selectByExample(null);
    }

//    /**
//     * 按分页查询
//     */
//    public PageResult findPage(int pageNum, int pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        Page<TbOrder> page = (Page<TbOrder>) orderMapper.selectByExample(null);
//        return new PageResult(page.getTotal(), page.getResult());
//    }

    /**
     * 增加
     */
    @Override
    public void add(TbOrder order) {
        //设置默认参数
        if (order != null) {
            if (order.getNumber() == null)
                order.setNumber(1);
            if (order.getStatus() == null)
                order.setStatus(DEFAULT_STATUS_NOPAY);  //未支付

            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            orderMapper.insert(order);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(TbOrder order) {
        order.setUpdateTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbOrder findOne(Long id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            orderMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbOrder order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();

        if (order != null && order.getBuyerId() != null) {
            criteria.andBuyerIdEqualTo(order.getBuyerId());
        } else if (order != null && order.getStatus() != null) {
            criteria.andStatusEqualTo(order.getStatus());
        } else {
            ArrayList<Byte> statusList = new ArrayList<>();
            statusList.add(DEFAULT_STATUS_NOPAY);
            statusList.add(DEFAULT_STATUS_PAY);
            statusList.add(DEFAULT_STATUS_GETED);
            criteria.andStatusIn(statusList);
        }

        Page<TbOrder> page = (Page<TbOrder>) orderMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 分页查询订单  TODO  分页查询
     */
    @Override
    public Result findPage(int pageNum, int pageSize, TbUser user, TbOrder order) {
        PageHelper.startPage(pageNum, pageSize);

        TbOrderExample oe = new TbOrderExample();
        TbOrderExample.Criteria criteria = oe.createCriteria();


        if (order != null && order.getBuyerId() != null) {
            criteria.andBuyerIdEqualTo(order.getBuyerId());
        } else {
            criteria.andBuyerIdEqualTo(user.getId());
        }
        if (order != null && order.getStatus() != null) {
            criteria.andStatusEqualTo(order.getStatus());
        } else {
            ArrayList<Byte> statusList = new ArrayList<>();
            statusList.add(DEFAULT_STATUS_NOPAY);
            statusList.add(DEFAULT_STATUS_PAY);
            statusList.add(DEFAULT_STATUS_GETED);
            criteria.andStatusIn(statusList);
        }

        Page<TbOrder> page = (Page<TbOrder>) orderMapper.selectByExample(oe);
        return new Result(true, "查询成功", new PageResult(page.getTotal(), page.getResult()));
    }

//==我是神奇的分割线=======================================================================================================


    /**
     * 添加订单
     *
     * @Param order  订单实体
     */
    @Override
    public void addOrder(TbOrder order) {
//        private Integer number;
//        private Date createTime;
//        private Date updateTime;
//        private Byte status;
        if (order.getNumber() == null) order.setNumber(1);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setStatus(DEFAULT_STATUS_NOPAY);

        orderMapper.insert(order);

    }

    @Override
    public PageResult newSearch(TbOrder order, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

//        private Long buyerId;  //查询指定卖家的订单
//        private Byte status;   //查询指定状态的订单
        TbOrderExample toe = new TbOrderExample();
        TbOrderExample.Criteria criteria = toe.createCriteria();

        if (order.getBuyerId() != null) criteria.andBuyerIdEqualTo(order.getBuyerId());
        if (order.getSellerId() != null) criteria.andSellerIdEqualTo(order.getSellerId());
        if (order.getStatus() != null) criteria.andStatusEqualTo(order.getStatus());

        Page<TbOrder> page = (Page<TbOrder>) orderMapper.selectByExample(toe);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<TbOrder> findOrderByBuyerId(Long buyerId) {

        TbOrderExample toe = new TbOrderExample();
        TbOrderExample.Criteria criteria = toe.createCriteria();

        criteria.andBuyerIdEqualTo(buyerId);
        criteria.andStatusEqualTo(DEFAULT_STATUS_FINSHED);  //订单状态  1 未支付  2 已支付  3 取消订单 4已完成

        List<TbOrder> orders = orderMapper.selectByExample(toe);

        return orders;
    }

}
