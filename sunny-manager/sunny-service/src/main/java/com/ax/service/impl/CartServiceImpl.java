package com.ax.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.mapper.TbCartMapper;
import com.ax.mapper.TbContentMapper;
import com.ax.mapper.TbGoodsMapper;
import com.ax.mapper.TbImageMapper;
import com.ax.pojo.*;
import com.ax.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class CartServiceImpl implements CartService {

    private static final int DEFAULT_GOODS_NUMBER = 1;  //默认商品数

    private static final int CART_STATUS_NORAML = 1;  //购物车状态  1 表示正常 2 删除

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbCartMapper cartMapper;

    @Autowired
    private TbImageMapper imageMapper;

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbCart> findAll() {
        return cartMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbCart> page = (Page<TbCart>) cartMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbCart cart) {

        // 修改cart的一些数据
        if (cart != null) {
            if (cart.getNumber() == null) {
                cart.setNumber(DEFAULT_GOODS_NUMBER);
            }
            cart.setStatus(CART_STATUS_NORAML);  // 1 表示正常
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            cartMapper.insert(cart);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(TbCart cart) {

        cart.setUpdateTime(new Date());

        cartMapper.updateByPrimaryKeySelective(cart);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Result findOne(Long id) {

        List list = new ArrayList();

        TbCart tbCart = cartMapper.selectByPrimaryKey(id);
        list.add(tbCart);

        if (tbCart.getGoodsId() != null) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(tbCart.getGoodsId());
            list.add(tbGoods);
            if (tbGoods.getContentId() != null) {
                TbContent tbContent = contentMapper.selectByPrimaryKey(tbGoods.getContentId());
                list.add(tbContent);
            }
            TbImage tbImage = imageMapper.selectOneByKindId(tbGoods.getId());
            list.add(tbImage);
        }

        return new Result(true, "查询成功", list);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            cartMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public Result findPage(TbCart cart, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbCartExample example = new TbCartExample();
        TbCartExample.Criteria criteria = example.createCriteria();

        if (cart != null) {
            if (cart.getBuyerId() != null) {
                criteria.andBuyerIdEqualTo(cart.getBuyerId());   //买家id
            }
        }

        Page<TbCart> page = (Page<TbCart>) cartMapper.selectByExample(example);

        List list = new ArrayList();
        for (TbCart tbCart : page.getResult()) {
            if (tbCart.getId() != null) {
                list.add(findOne(tbCart.getId()));
            }

        }
        return new Result(true, "查询成功", list);
    }

}
