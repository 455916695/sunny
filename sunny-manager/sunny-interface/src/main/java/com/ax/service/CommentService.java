package com.ax.service;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.pojo.GoodsComment;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface CommentService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<GoodsComment> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(GoodsComment comment);
	
	
	/**
	 * 修改
	 */
	public void update(GoodsComment comment);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public GoodsComment findOne(String id);
	
	
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
	public PageResult findPage(GoodsComment comment, int pageNum, int pageSize);
	
}
