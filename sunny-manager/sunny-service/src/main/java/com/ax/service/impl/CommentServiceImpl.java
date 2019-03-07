package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.TbCommentMapper;
import com.ax.pojo.TbComment;
import com.ax.pojo.TbCommentExample;
import com.ax.service.CommentService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private TbCommentMapper commentMapper;
	
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
		Page<TbComment> page=   (Page<TbComment>) commentMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbComment comment) {
		commentMapper.insert(comment);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbComment comment){
		commentMapper.updateByPrimaryKey(comment);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbComment findOne(Long id){
		return commentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			commentMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbComment comment, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbCommentExample example=new TbCommentExample();
		TbCommentExample.Criteria criteria = example.createCriteria();
		
		if(comment!=null){			
				
		}
		
		Page<TbComment> page= (Page<TbComment>)commentMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
