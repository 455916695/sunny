package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.GoodsCommentMapper;
import com.ax.pojo.GoodsComment;
import com.ax.pojo.GoodsCommentExample;
import com.ax.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private GoodsCommentMapper commentMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<GoodsComment> findAll() {
		return commentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<GoodsComment> page=   (Page<GoodsComment>) commentMapper.selectByExample(new GoodsCommentExample());
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(GoodsComment comment) {
		commentMapper.insert(comment);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(GoodsComment comment){
		commentMapper.updateByPrimaryKey(comment);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public GoodsComment findOne(String id){
		return commentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			commentMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(GoodsComment comment, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		GoodsCommentExample example=new GoodsCommentExample();
		GoodsCommentExample.Criteria criteria = example.createCriteria();
		
		if(comment!=null){			
						if(comment.getUuid()!=null && comment.getUuid().length()>0){
				criteria.andUuidLike("%"+comment.getUuid()+"%");
			}
			if(comment.getGid()!=null && comment.getGid().length()>0){
				criteria.andGidLike("%"+comment.getGid()+"%");
			}
			if(comment.getComment()!=null && comment.getComment().length()>0){
				criteria.andCommentLike("%"+comment.getComment()+"%");
			}
	
		}
		
		Page<GoodsComment> page= (Page<GoodsComment>)commentMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
