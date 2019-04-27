package com.ax.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ax.entity.Goods;
import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.mapper.TbContentMapper;
import com.ax.mapper.TbGoodsMapper;
import com.ax.mapper.TbImageMapper;
import com.ax.mapper.TbTypeMapper;
import com.ax.pojo.*;
import com.ax.pojogroup.NewGoods;
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
    private static final int NORMAL_GOODS_STATUS = 1;  //1.正常状态  2.被加入购物车  3.已售出
    private static final int NORMAL_IMAGE_STATUS = 1;  //状态  1 正常  2 删除
    private static final int DEFAULT_NUMBER = 1;  //1 默认数量为1
    private static final int DEFAULT_PRICE = 0;  //1 默认数量为1

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private TbImageMapper imageMapper;


    @Autowired
    private TbTypeMapper typeMapper;

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
    public Result findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new Result(true, "查询成功", new PageResult(page.getTotal(), page.getResult()));
    }

    /**
     * 增加
     */
    @Override
    public void add(Goods goods, TbUser user) throws Exception {
        //添加商品id
        TbGoods tbGoods = new TbGoods();
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
//                tbContent.setId(idWorker.nextId());  //自增长
                tbContent.setContents(goods.getContent());
                contentMapper.insert(tbContent);  //保存描述
                tbGoods.setContentId(tbContent.getId());
            }

            goodsMapper.insert(tbGoods);
//保存图片
            List<TbImage> imageList = goods.getImageList();
            if (imageList != null && imageList.size() > 0) {
                for (TbImage tbImage : imageList) {
//                    tbImage.setId(idWorker.nextId()); //自增长
                    tbImage.setKind(KIND_SHOP); //图片种类 1 用户头像  2 商品图片
                    tbImage.setKindId(tbGoods.getId());
                    tbImage.setStatus(NORMAL_IMAGE_STATUS);//状态  1 正常  2 删除
                    tbImage.setCreateTime(new Date());
                    tbImage.setUpdateTime(new Date());
                    imageMapper.insert(tbImage);
                }
            }
        } else {
            throw new Exception("添加失败:非法参数异常");
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(Goods goods) {
        TbGoods tbGoods = new TbGoods(goods);
        if (goods.getContentId() != null) {
            TbContent tbContent = new TbContent();
            tbContent.setId(goods.getContentId());
            tbContent.setContents(goods.getContent());
            contentMapper.updateByPrimaryKeySelective(tbContent);
        }
        goodsMapper.updateByPrimaryKeySelective(tbGoods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findOne(Long id) {
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        Goods goods = new Goods(tbGoods);
        //查询图片
        TbImageExample tie = new TbImageExample();
        TbImageExample.Criteria criteria = tie.createCriteria();
        criteria.andKindEqualTo(KIND_SHOP);   //表示查询的是商品图片
        criteria.andKindIdEqualTo(id);        //商品id
        List<TbImage> tbImages = imageMapper.selectByExample(tie);//
        goods.setImageList(tbImages);
        //查询商品描述
        if (tbGoods.getContentId() != null && tbGoods.getContentId() != 0) {
            TbContent tbContent = contentMapper.selectByPrimaryKey(tbGoods.getContentId());
            goods.setContent(tbContent.getContents());
        }
//        查询商品类型
        byte typeId = tbGoods.getTypeId();
        if (typeId != 0) {
            TbType tbType = typeMapper.selectByPrimaryKey((long) typeId);//TODO 此处是一个设计错误，id 的类型设置有误
            goods.setType(tbType.getType());
        }
        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {

        //进行逻辑删除  3 表示删除
        goodsMapper.updateStatus(ids, 3);
//
//        for (Long id : ids) {
//            goodsMapper.deleteByPrimaryKey(id);
//        }
    }


    @Override
    public Result findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = example.createCriteria();

        if (goods != null) {
            if (goods.getName() != null && goods.getName().length() > 0) {
                criteria.andNameLike("%" + goods.getName() + "%");
            }
            if (goods.getTypeId() != null) {
                criteria.andTypeIdEqualTo(goods.getTypeId());
            }
            if (goods.getStatus() != null) {
                criteria.andStatusEqualTo(goods.getStatus());
            }
        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);

//        此处 查询商品详情
        List<TbGoods> result = page.getResult();
        List<Goods> goodsList = new ArrayList<>();
        for (TbGoods tbGoods : result) {
            Goods one = findOne(tbGoods.getId());
            goodsList.add(one);
        }
        PageResult pageResult = new PageResult(page.getTotal(), goodsList);
        return new Result(true, "访问成功", pageResult);
    }


    //===============我神奇的分界线======================================================================================================

    /**
     * 添加商品
     */
    public TbGoods addGoods(TbGoods goods) {
        if (goods != null) {

            if (goods.getNumber() == null) goods.setNumber(DEFAULT_NUMBER);  //设置默认数量
            goods.setStatus(NORMAL_GOODS_STATUS);                             //设置状态
            goods.setCreateTime(new Date());                                   //设置创建时间
            goods.setUpdateTime(new Date());                                   //设置更新时间

            goodsMapper.insert(goods);

            return goods;
        } else {
            return null;
        }
    }

    /**
     * 查询指定商品信息
     */
    @Override
    public TbGoods findOneTbGoods(Long id) {

        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);

        return tbGoods;
    }

    /**
     * 查询全部商品信息
     */
    @Override
    public List<TbGoods> findAllTbGoods() {

        List<TbGoods> tbGoods = goodsMapper.selectByExample(null); //其实这里可以考虑改一下

        return tbGoods;
    }

    /**
     * 分页查询商品
     */
    @Override
    public PageResult findPageTbGoods(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);  //设置分页

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null); //查询分页数据

        return new PageResult(page.getTotal(), page.getResult());  //返回分页结果
    }

    /**
     * 条件分页查询商品
     */
    @Override
    public PageResult search(TbGoods tbGoods, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

//        name                  // 商品名称
//          Long sellerId;       //卖家id
//          Byte typeId;        //商品类型
//          Integer price;      //商品价格
//          Byte oldDegree;     //商品新旧程度

        TbGoodsExample tge = new TbGoodsExample();
        TbGoodsExample.Criteria criteria = tge.createCriteria();

        if (!StringUtils.isEmpty(tbGoods.getName())) criteria.andNameLike("%" + tbGoods.getName() + "%");

        if (tbGoods.getSellerId() != null) criteria.andSellerIdEqualTo(tbGoods.getSellerId());

        if (tbGoods.getTypeId() != null) criteria.andTypeIdEqualTo(tbGoods.getTypeId());

        if (tbGoods.getPrice() != null) criteria.andPriceBetween(0, tbGoods.getPrice());

        if (tbGoods.getOldDegree() != null) criteria.andOldDegreeEqualTo(tbGoods.getOldDegree());

        if (tbGoods.getStatus() != null) criteria.andStatusEqualTo(tbGoods.getStatus());

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(tge); //查询分页数据

        return new PageResult(page.getTotal(), page.getResult());  //返回分页结果
    }

    /**
     * 根据商品id 修改状态
     */
    @Override
    public void updateStatusById(Long goodsId, int status) {

        TbGoods tbGoods = new TbGoods();
        tbGoods.setId(goodsId);
        tbGoods.setStatus(status);
        goodsMapper.updateByPrimaryKeySelective(tbGoods);

    }

    @Override
    public boolean findGoodsIsCanBuy(Long goodsId) {

        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(goodsId);

        //  1 表示正常  2 表示被买走了  3 表示被删除了
        if (tbGoods.getStatus() == 1) {
            return true;
        }
        return false;
    }

}
