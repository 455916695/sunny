package com.ax.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.mapper.TbCartMapper;
import com.ax.mapper.TbOrderMapper;
import com.ax.pojo.*;
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


    @Autowired
    private TbCartMapper cartMapper;

    private static final Byte DEFAULT_STATUS_NOPAY = 1; // 1 未支付  2 已支付未发货  3 已支付已发货  4 已支付已收货 5评价 6 删除
    private static final Byte DEFAULT_STATUS_PAY = 2; //   1 未支付  2 已支付未发货  3 已支付已发货  4 已支付已收货 5评价 6 删除
    private static final Byte DEFAULT_STATUS_SEND = 3; // 1 未支付  2 已支付未发货  3 已支付已发货  4 已支付已收货  5评价 6 删除
    private static final Byte DEFAULT_STATUS_GOT = 4; //1 未支付  2 已支付未发货  3 已支付已发货  4 已支付已收货  5评价 6 删除
    private static final Byte DEFAULT_STATUS_COMMENT = 5; //1 未支付  2 已支付未发货  3 已支付已发货  4 已支付已收货  5评价 6 删除
    private static final Byte DEFAULT_STATUS_DELETE = 6; //1 未支付  2 已支付未发货  3 已支付已发货  4 已支付已收货  5评价 6 删除

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
            //生成订单
            if (order.getNumber() == null)
                order.setNumber(1);
            if (order.getStatus() == null)
                order.setStatus(DEFAULT_STATUS_NOPAY);  //未支付

            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            orderMapper.insert(order);

            //修改购物车数据状态  为2 以生成订单  buyerId  goodsId
            cartMapper.updateStatusByBuyerIdAndGoodsId(order.getBuyerId(), order.getGoodsId(), 2);
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

        //进行逻辑删除
        orderMapper.updateStatus(ids, DEFAULT_STATUS_DELETE);
//        for (Long id : ids) {
//            orderMapper.deleteByPrimaryKey(id);
//        }
    }


    @Override
    public PageResult findPage(TbOrder order, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();

        if (order != null && order.getBuyerId() != null) {
            criteria.andBuyerIdEqualTo(order.getBuyerId());
        }
        if (order != null && order.getStatus() != null) {
            criteria.andStatusEqualTo(order.getStatus());
        } else {
            ArrayList<Byte> statusList = new ArrayList<>();
            statusList.add(DEFAULT_STATUS_NOPAY);
            statusList.add(DEFAULT_STATUS_PAY);
            statusList.add(DEFAULT_STATUS_SEND);
            statusList.add(DEFAULT_STATUS_GOT);
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
            statusList.add(DEFAULT_STATUS_GOT);
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


        //删除购物车中的数据
        TbCartExample ce = new TbCartExample();
        ce.createCriteria().andBuyerIdEqualTo(order.getBuyerId()).andGoodsIdEqualTo(order.getGoodsId());
        cartMapper.deleteByExample(ce);

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

    /**
     * 查询根据条件查询 订单
     */
    @Override
    public List<TbOrder> findOrderByBuyerId(TbOrder order) {
        TbOrderExample toe = new TbOrderExample();
        TbOrderExample.Criteria criteria = toe.createCriteria();

        //添加买家id
        criteria.andBuyerIdEqualTo(order.getBuyerId());
        //添加订单状态
        if (order.getStatus() != null) {
            criteria.andStatusEqualTo(order.getStatus());
        } else {
            //如果没有传入指定状态，则查询 已收货和 已评论的 订单
            List<Byte> statusList = new ArrayList();
            statusList.add(DEFAULT_STATUS_GOT);
            statusList.add(DEFAULT_STATUS_COMMENT);
            criteria.andStatusIn(statusList);
        }

        List<TbOrder> orders = orderMapper.selectByExample(toe);

        return orders;
    }


    /**
     * 修改指定订单状态
     */
    @Override
    public void updateStatusByBuyerIdAndGoodsId(Long buyerId, Long goodsId, byte status) {
        TbOrder order = new TbOrder();
        order.setStatus(status);

        TbOrderExample oe = new TbOrderExample();
        oe.createCriteria().andBuyerIdEqualTo(buyerId).andGoodsIdEqualTo(goodsId);

        orderMapper.updateByExampleSelective(order, oe);
    }

}
