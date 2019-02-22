package com.ax.service;
import com.ax.entity.PageResult;
import com.ax.pojo.GoodsImg;

import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface ImgService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<GoodsImg> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(GoodsImg img);
	
	
	/**
	 * 修改
	 */
	public void update(GoodsImg img);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public GoodsImg findOne(String id);
	
	
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
	public PageResult findPage(GoodsImg img, int pageNum, int pageSize);
	
}
