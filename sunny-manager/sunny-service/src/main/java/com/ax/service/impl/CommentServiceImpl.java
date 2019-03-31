package com.ax.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ax.entity.Comment;
import com.ax.entity.Goods;
import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.mapper.*;
import com.ax.pojo.*;
import com.ax.service.CommentService;
import com.github.pagehelper.Page;
import org.junit.jupiter.api.Test;
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
public class CommentServiceImpl implements CommentService {

    @Autowired
    private TbCommentMapper commentMapper;

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private TbImageMapper imageMapper;

    @Autowired
    private TbGoodsMapper goodsMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbComment> findAll() {
        return commentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbComment> page = (Page<TbComment>) commentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Comment comment) {
        //整理出相应的需要保存的内容对象
        TbComment tbComment = null;
        if (comment != null) {
            tbComment = new TbComment();
            value(tbComment, comment);    //将comment 中的值赋值给tbComment
            if (comment.getTbContent() != null && !StringUtils.isEmpty(comment.getTbContent().getContents())) {
                TbContent content = new TbContent();
                content.setContents(comment.getTbContent().getContents());
                contentMapper.insert(content);
                tbComment.setContentId(content.getId());
            }
            commentMapper.insert(tbComment);
        }
    }

    /**
     * 为相关对象赋值
     */
    private void value(TbComment tbComment, Comment comment) {
        if (tbComment != null && comment != null) {
            if (comment.getBuyerId() != null) tbComment.setBuyerId(comment.getBuyerId());
            if (comment.getSellerId() != null) tbComment.setSellerId(comment.getSellerId());
            if (comment.getGoodsId() != null) tbComment.setGoodsId(comment.getGoodsId());
            tbComment.setCreateTime(new Date());
            tbComment.setUpdateTime(new Date());
        }
    }

    /**
     * 修改
     */
    @Override
    public void update(Comment comment) {
        //修改
        //判断所传参数是否正确
        if (comment != null && comment.getTbContent() != null && !StringUtils.isEmpty(comment.getTbContent().getContents())) {
            TbContent tbContent = new TbContent();
            tbContent.setId(comment.getTbContent().getId());
            tbContent.setContents(comment.getTbContent().getContents());
            contentMapper.updateByPrimaryKeySelective(tbContent);
            //此处是否需要 更新
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public List findOne(Long id) { //TODO  此处暂时不写，留下明天写
        //查询
        TbComment tbComment = commentMapper.selectByPrimaryKey(id);
        List list = new ArrayList();
        if (tbComment != null && tbComment.getGoodsId() != null) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(tbComment.getGoodsId());
            list.add(tbGoods);  //添加商品
            if (tbGoods.getContentId() != null) {
                TbContent tbContent = contentMapper.selectByPrimaryKey(tbGoods.getContentId());
                list.add(tbContent);   //添加描述
            }
            TbImage image = imageMapper.selectOneByKindId(tbGoods.getId()); //TODO 此处还没有写
            list.add(image);
        }
        if (tbComment != null && tbComment.getSellerId() != null) {
            TbUser tbUser = userMapper.selectByPrimaryKey(tbComment.getSellerId());
            list.add(tbUser);
            TbImage image = imageMapper.selectOneByKindId(tbUser.getId());
            list.add(image);
        }
        if (tbComment != null && tbComment.getBuyerId() != null) {
            TbUser tbUser = userMapper.selectByPrimaryKey(tbComment.getBuyerId());
            list.add(tbUser);
            TbImage image = imageMapper.selectOneByKindId(tbUser.getId());
            list.add(image);
        }
        if (tbComment != null && tbComment.getContentId() != null) {
            TbContent tbContent = contentMapper.selectByPrimaryKey(tbComment.getContentId());
            list.add(tbContent);
        }
        return list;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            commentMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public Result findPage(TbComment comment, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbCommentExample example = new TbCommentExample();
        TbCommentExample.Criteria criteria = example.createCriteria();

        if (comment != null) {
            if (comment.getSellerId() != null) {
                criteria.andSellerIdEqualTo(comment.getSellerId());
            }
            if (comment.getBuyerId() != null) {
                criteria.andBuyerIdEqualTo(comment.getBuyerId());
            }
            if (comment.getGoodsId() != null) {
                criteria.andBuyerIdEqualTo(comment.getGoodsId());
            }
        }
        Page<TbComment> page = (Page<TbComment>) commentMapper.selectByExample(example);

        List dataList = new ArrayList();

        for (TbComment tbComment : page.getResult()) {
            if (tbComment.getId() != null) {
                List one = findOne(tbComment.getId());
                dataList.add(one);
            }
        }
        return new Result(true, "查询成功", dataList);
    }

}
