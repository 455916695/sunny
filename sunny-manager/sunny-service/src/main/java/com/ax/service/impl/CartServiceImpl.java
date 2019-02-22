package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.CartMapper;
import com.ax.pojo.Cart;
import com.ax.pojo.CartExample;
import com.ax.service.CartService;
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
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<Cart> findAll() {
		return cartMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<Cart> page=   (Page<Cart>) cartMapper.selectByExample(new CartExample());
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Cart cart) {
		cartMapper.insert(cart);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Cart cart){
		cartMapper.updateByPrimaryKey(cart);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Cart findOne(String id){
		return cartMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			cartMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(Cart cart, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		CartExample example=new CartExample();
		CartExample.Criteria criteria = example.createCriteria();
		
		if(cart!=null){			
						if(cart.getUuid()!=null && cart.getUuid().length()>0){
				criteria.andUuidLike("%"+cart.getUuid()+"%");
			}
			if(cart.getUid()!=null && cart.getUid().length()>0){
				criteria.andUidLike("%"+cart.getUid()+"%");
			}
			if(cart.getGid()!=null && cart.getGid().length()>0){
				criteria.andGidLike("%"+cart.getGid()+"%");
			}
	
		}
		
		Page<Cart> page= (Page<Cart>)cartMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
