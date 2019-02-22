package com.ax.service.impl;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.GoodsImgMapper;
import com.ax.pojo.GoodsImg;
import com.ax.pojo.GoodsImgExample;
import com.ax.service.ImgService;
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
public class ImgServiceImpl implements ImgService {

	@Autowired
	private GoodsImgMapper imgMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<GoodsImg> findAll() {
		return imgMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<GoodsImg> page=   (Page<GoodsImg>) imgMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(GoodsImg img) {
		imgMapper.insert(img);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(GoodsImg img){
		imgMapper.updateByPrimaryKey(img);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public GoodsImg findOne(String id){
		return imgMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			imgMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(GoodsImg img, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		GoodsImgExample example=new GoodsImgExample();
		GoodsImgExample.Criteria criteria = example.createCriteria();
		
		if(img!=null){			
						if(img.getUuid()!=null && img.getUuid().length()>0){
				criteria.andUuidLike("%"+img.getUuid()+"%");
			}
			if(img.getGid()!=null && img.getGid().length()>0){
				criteria.andGidLike("%"+img.getGid()+"%");
			}
			if(img.getImg1()!=null && img.getImg1().length()>0){
				criteria.andImg1Like("%"+img.getImg1()+"%");
			}
			if(img.getImg2()!=null && img.getImg2().length()>0){
				criteria.andImg2Like("%"+img.getImg2()+"%");
			}
			if(img.getImg3()!=null && img.getImg3().length()>0){
				criteria.andImg3Like("%"+img.getImg3()+"%");
			}
			if(img.getImg4()!=null && img.getImg4().length()>0){
				criteria.andImg4Like("%"+img.getImg4()+"%");
			}
			if(img.getImg5()!=null && img.getImg5().length()>0){
				criteria.andImg5Like("%"+img.getImg5()+"%");
			}
	
		}
		
		Page<GoodsImg> page= (Page<GoodsImg>)imgMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
