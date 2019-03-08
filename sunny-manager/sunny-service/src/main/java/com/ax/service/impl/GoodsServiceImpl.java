package com.ax.service.impl;

import java.util.Date;
import java.util.List;

import com.ax.entity.Goods;
import com.ax.entity.PageResult;
import com.ax.mapper.TbContentMapper;
import com.ax.mapper.TbGoodsMapper;
import com.ax.mapper.TbImageMapper;
import com.ax.pojo.*;
import com.ax.service.GoodsService;
import com.ax.util.IdWorker;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {


    private static final byte DEFAULT_TYPE = 0;  //0 表示其他类型
    private static final byte DEFAULT_DEGREE = 0;  //0 表示默认新旧程度
    private static final byte DEFAULT_MEANS = 0;  //0 表示默认销售方式
    private static final int KIND_SHOP = 2;  //图片种类 1 用户头像  2 商品图片
    private static final int NORMAL_GOODS_STATUS = 1;  //1 正常状态
    private static final int NORMAL_IMAGE_STATUS = 1;  //状态  1 正常  2 删除
    private static final int DEFAULT_NUMBER = 1;  //1 默认数量为1
    private static final int DEFAULT_PRICE = 0;  //1 默认数量为1

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private TbImageMapper imageMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Goods goods, TbUser user) throws Exception {
        IdWorker idWorker = new IdWorker();
        //添加商品id
        TbGoods tbGoods = new TbGoods();
        tbGoods.setId(idWorker.nextId());
        if (goods != null) { //TODO 此处需要校验，什么样的商品算空
            if (!StringUtils.isEmpty(goods.getName())) {//设置商品名称
                tbGoods.setName(goods.getName());
            } else {
                tbGoods.setName("未命名");
            }
            if (goods.getSellerId() != null) {  //设置卖家id
                tbGoods.setSellerId(goods.getSellerId());
            } else {
                tbGoods.setSellerId(user.getId());
            }
            if (goods.getTypeId() != null) {
                tbGoods.setTypeId(goods.getTypeId());
            } else {
                tbGoods.setTypeId(DEFAULT_TYPE);  //0 表示其他类型
            }
            if (goods.getNumber() != null) {
                tbGoods.setNumber(goods.getNumber());
            } else {
                tbGoods.setNumber(DEFAULT_NUMBER);  //0 表示其他类型
            }
            if (goods.getPrice() != null) {
                tbGoods.setPrice(goods.getPrice());  //此处可能会存在修改
            } else {
                tbGoods.setPrice(DEFAULT_PRICE);  //0 表示其他类型
            }
            if (goods.getOldDegree() != null) {
                tbGoods.setOldDegree(goods.getOldDegree());  //此处可能会存在修改
            } else {
                tbGoods.setOldDegree(DEFAULT_DEGREE);  //0 表示其他类型
            }
            if (goods.getMeans() != null) {
                tbGoods.setMeans(goods.getMeans());  //此处可能会存在修改
            } else {
                tbGoods.setMeans(DEFAULT_MEANS);  //0 表示其他类型
            }
            tbGoods.setStatus(NORMAL_GOODS_STATUS);
            tbGoods.setCreateTime(new Date());
            tbGoods.setUpdateTime(new Date());
//保存描述
            if (!StringUtils.isEmpty(goods.getContent())) {
                TbContent tbContent = new TbContent();
                tbContent.setId(idWorker.nextId());
                tbContent.setContents(goods.getContent());
                contentMapper.insert(tbContent);  //保存描述
                tbGoods.setContentId(tbContent.getId());
            }
//保存图片
            List<String> imageList = goods.getImageList();
            if (imageList != null && imageList.size() > 0) {
                TbImage tbImage = null;
                for (String imageAddress : imageList) {
                    tbImage = new TbImage();
                    tbImage.setId(idWorker.nextId());
                    tbImage.setKind(KIND_SHOP); //图片种类 1 用户头像  2 商品图片
                    tbImage.setKindId(tbGoods.getId());
                    tbImage.setAddress(imageAddress);
                    tbImage.setStatus(NORMAL_IMAGE_STATUS);//状态  1 正常  2 删除
                    tbImage.setCreateTime(new Date());
                    tbImage.setUpdateTime(new Date());
                    imageMapper.insert(tbImage);
                }
            }
        }else {
            throw  new Exception("添加失败:非法参数异常");
        }
        goodsMapper.insert(tbGoods);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbGoods goods) {
        goodsMapper.updateByPrimaryKey(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbGoods findOne(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            goodsMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = example.createCriteria();

        if (goods != null) {
            if (goods.getName() != null && goods.getName().length() > 0) {
                criteria.andNameLike("%" + goods.getName() + "%");
            }

        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

}
