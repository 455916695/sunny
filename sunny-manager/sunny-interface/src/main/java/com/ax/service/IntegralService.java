package com.ax.service;
import com.ax.entity.PageResult;
import com.ax.pojo.Integral;

import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface IntegralService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Integral> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Integral integral);
	
	
	/**
	 * 修改
	 */
	public void update(Integral integral);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Integral findOne(String id);
	
	
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
	public PageResult findPage(Integral integral, int pageNum, int pageSize);
	
}
