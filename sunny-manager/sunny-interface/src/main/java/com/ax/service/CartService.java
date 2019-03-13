package com.ax.service;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbCart;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface CartService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbCart> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbCart cart);
	
	
	/**
	 * 修改
	 */
	public void update(TbCart cart);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Result findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public Result findPage(TbCart cart, int pageNum, int pageSize);
	
}
