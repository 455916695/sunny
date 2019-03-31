package com.ax.service;

import java.util.List;

import com.ax.entity.Goods;
import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbGoods;
import com.ax.pojo.TbUser;
import com.ax.pojogroup.NewGoods;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface GoodsService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<TbGoods> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public Result findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(Goods goods, TbUser user) throws Exception;


    /**
     * 修改
     */
    public void update(Goods goods);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public Goods findOne(Long id);


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
    public Result findPage(TbGoods goods, int pageNum, int pageSize);
//======我是神奇的分界线=====================================================================================================

    /**
     * 添加商品信息
     *
     * @param goods 商品信息
     */
    TbGoods addGoods(TbGoods goods);


    /**
     * 查询某个商品详情（详细的查询）
     */
    TbGoods findOneTbGoods(Long id);

    /**
     * 查询全部商品（粗略的查询）
     */
    List<TbGoods> findAllTbGoods();

    /**
     * 分页查询
     */
    PageResult findPageTbGoods(int pageNum, int pageSize);

    /**
     * 条件分页查询
     */
    PageResult search(TbGoods tbGoods, int pageNum, int pageSize);


}
