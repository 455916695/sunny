package com.ax.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.mapper.UserMapper;
import com.ax.pojo.User;
import com.ax.pojo.UserExample;
import com.ax.service.UserService;
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
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<User> findAll() {
		return userMapper.selectByExample(null);
	}


	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<User> page=   (Page<User>) userMapper.selectByExample(new UserExample());
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(User user) {
		userMapper.insert(user);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(User user){
		userMapper.updateByPrimaryKey(user);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public User findOne(String id){
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			userMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(User user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		UserExample example=new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		
		if(user!=null){			
						if(user.getUuid()!=null && user.getUuid().length()>0){
				criteria.andUuidLike("%"+user.getUuid()+"%");
			}
			if(user.getName()!=null && user.getName().length()>0){
				criteria.andNameLike("%"+user.getName()+"%");
			}
			if(user.getPassword()!=null && user.getPassword().length()>0){
				criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(user.getEmail()!=null && user.getEmail().length()>0){
				criteria.andEmailLike("%"+user.getEmail()+"%");
			}
			if(user.getPhone()!=null && user.getPhone().length()>0){
				criteria.andPhoneLike("%"+user.getPhone()+"%");
			}
			if(user.getWechat()!=null && user.getWechat().length()>0){
				criteria.andWechatLike("%"+user.getWechat()+"%");
			}
			if(user.getSchool()!=null && user.getSchool().length()>0){
				criteria.andSchoolLike("%"+user.getSchool()+"%");
			}
	
		}

		Page<User> page= (Page<User>)userMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}
	
}
