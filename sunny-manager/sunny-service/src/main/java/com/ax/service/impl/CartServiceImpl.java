package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.TbCartMapper;
import com.ax.pojo.TbCart;
import com.ax.pojo.TbCartExample;
import com.ax.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private TbCartMapper cartMapper;
	
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
		Page<TbCart> page=   (Page<TbCart>) cartMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbCart cart) {
		cartMapper.insert(cart);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbCart cart){
		cartMapper.updateByPrimaryKey(cart);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbCart findOne(Long id){
		return cartMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			cartMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbCart cart, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbCartExample example=new TbCartExample();
		TbCartExample.Criteria criteria = example.createCriteria();
		
		if(cart!=null){			
				
		}
		
		Page<TbCart> page= (Page<TbCart>)cartMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
