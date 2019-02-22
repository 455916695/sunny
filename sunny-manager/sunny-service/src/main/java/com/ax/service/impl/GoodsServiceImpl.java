package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.GoodsMapper;
import com.ax.pojo.Goods;
import com.ax.pojo.GoodsExample;
import com.ax.service.GoodsService;
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
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<Goods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<Goods> page=   (Page<Goods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		goodsMapper.insert(goods);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
		goodsMapper.updateByPrimaryKey(goods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(String id){
		return goodsMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			goodsMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(Goods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		GoodsExample example=new GoodsExample();
		GoodsExample.Criteria criteria = example.createCriteria();
		
		if(goods!=null){			
						if(goods.getUuid()!=null && goods.getUuid().length()>0){
				criteria.andUuidLike("%"+goods.getUuid()+"%");
			}
			if(goods.getDescription()!=null && goods.getDescription().length()>0){
				criteria.andDescriptionLike("%"+goods.getDescription()+"%");
			}
			if(goods.getDesimg()!=null && goods.getDesimg().length()>0){
				criteria.andDesimgLike("%"+goods.getDesimg()+"%");
			}
	
		}
		
		Page<Goods> page= (Page<Goods>)goodsMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
