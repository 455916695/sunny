package com.ax.service.impl;

import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.TbContentMapper;
import com.ax.pojo.TbContent;
import com.ax.pojo.TbContentExample;
import com.ax.service.ContentService;
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
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public TbContent add(TbContent content) {
        contentMapper.insert(content);
        return content;
    }


    /**
     * 修改
     */
    @Override
    public void update(TbContent content) {
        contentMapper.updateByPrimaryKeyWithBLOBs(content);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();

//        if (content != null) {
//            if (content.getContent() != null && content.getContent().length() > 0) {
//                criteria.andContentLike("%" + content.getContent() + "%");
//            }
//
//        }

        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

}
