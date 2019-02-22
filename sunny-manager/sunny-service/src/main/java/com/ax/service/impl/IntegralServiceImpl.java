package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.IntegralMapper;
import com.ax.pojo.Integral;
import com.ax.pojo.IntegralExample;
import com.ax.service.IntegralService;
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
public class IntegralServiceImpl implements IntegralService {

	@Autowired
	private IntegralMapper integralMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<Integral> findAll() {
		return integralMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<Integral> page=   (Page<Integral>) integralMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Integral integral) {
		integralMapper.insert(integral);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Integral integral){
		integralMapper.updateByPrimaryKey(integral);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Integral findOne(String id){
		return integralMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			integralMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(Integral integral, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		IntegralExample example=new IntegralExample();
		IntegralExample.Criteria criteria = example.createCriteria();
		
		if(integral!=null){			
						if(integral.getUuid()!=null && integral.getUuid().length()>0){
				criteria.andUuidLike("%"+integral.getUuid()+"%");
			}
			if(integral.getUid()!=null && integral.getUid().length()>0){
				criteria.andUidLike("%"+integral.getUid()+"%");
			}
	
		}
		
		Page<Integral> page= (Page<Integral>)integralMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
