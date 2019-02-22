package com.ax.service;
import com.ax.entity.PageResult;
import com.ax.pojo.Orders;

import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface OrdersService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Orders> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Orders orders);
	
	
	/**
	 * 修改
	 */
	public void update(Orders orders);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Orders findOne(String id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(String[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Orders orders, int pageNum, int pageSize);
	
}
