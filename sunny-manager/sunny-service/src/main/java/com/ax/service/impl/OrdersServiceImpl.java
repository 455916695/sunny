package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.OrdersMapper;
import com.ax.pojo.Orders;
import com.ax.pojo.OrdersExample;
import com.ax.service.OrdersService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
 ;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersMapper ordersMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<Orders> findAll() {
		return ordersMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<Orders> page=   (Page<Orders>) ordersMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Orders orders) {
		ordersMapper.insert(orders);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Orders orders){
		ordersMapper.updateByPrimaryKey(orders);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Orders findOne(String id){
		return ordersMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			ordersMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(Orders orders, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		OrdersExample example=new OrdersExample();
		OrdersExample.Criteria criteria = example.createCriteria();
		
		if(orders!=null){			
						if(orders.getUuid()!=null && orders.getUuid().length()>0){
				criteria.andUuidLike("%"+orders.getUuid()+"%");
			}
			if(orders.getSeller()!=null && orders.getSeller().length()>0){
				criteria.andSellerLike("%"+orders.getSeller()+"%");
			}
			if(orders.getBuyer()!=null && orders.getBuyer().length()>0){
				criteria.andBuyerLike("%"+orders.getBuyer()+"%");
			}
			if(orders.getGid()!=null && orders.getGid().length()>0){
				criteria.andGidLike("%"+orders.getGid()+"%");
			}
	
		}
		
		Page<Orders> page= (Page<Orders>)ordersMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
