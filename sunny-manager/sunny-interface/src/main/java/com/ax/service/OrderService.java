package com.ax.service;

import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbOrder;
import com.ax.pojo.TbUser;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface OrderService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbOrder> findAll();


//    /**
//     * 返回分页列表
//     *
//     * @return
//     */
//    public PageResult findPage(int pageNum, int pageSize, TbUser user, TbOrder order);


    /**
     * 增加
     */
    public void add(TbOrder order);


    /**
     * 修改
     */
    public void update(TbOrder order);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public TbOrder findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(TbOrder order, int pageNum, int pageSize);

    /**
     * 分页查询，订单管理
     */
    Result findPage(int pageNum, int pageSize, TbUser user, TbOrder order);

//=====我是神奇的分界线======================================================================================================

    /**
     * 添加订单
     */
    public void addOrder(TbOrder order);

    /**
     * 条件分页查询
     */
    public PageResult  newSearch(TbOrder order, int pageNum, int pageSize);

    /**
     *  根据买家id 查询订单
     * */
    List<TbOrder> findOrderByBuyerId(Long buyerId);
}
