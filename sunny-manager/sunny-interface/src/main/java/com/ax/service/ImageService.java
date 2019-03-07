package com.ax.service;
import java.io.File;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbImage;
import com.ax.pojo.TbUser;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface ImageService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbImage> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(TbImage image);
	
	
	/**
	 * 修改
	 */
	public void update(TbImage image);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public TbImage findOne(Long id);
	
	
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
	public PageResult findPage(TbImage image, int pageNum, int pageSize);

	File upload(String servicePath,String originalFilename, int kind, TbUser user);
}
