package com.ax.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.mapper.UserMapper;
import com.ax.pojo.User;
import com.ax.pojo.UserExample;
import com.ax.service.UserService;
import com.ax.util.MD5Utils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

/**
 * 服务实现层
 *
 * @author Administrator
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
        Page<User> page = (Page<User>) userMapper.selectByExample(new UserExample());
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
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public User findOne(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            userMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(User user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if (user != null) {
            if (user.getUuid() != null && user.getUuid().length() > 0) {
                criteria.andUuidLike("%" + user.getUuid() + "%");
            }
            if (user.getName() != null && user.getName().length() > 0) {
                criteria.andNameLike("%" + user.getName() + "%");
            }
            if (user.getPassword() != null && user.getPassword().length() > 0) {
                criteria.andPasswordLike("%" + user.getPassword() + "%");
            }
            if (user.getEmail() != null && user.getEmail().length() > 0) {
                criteria.andEmailLike("%" + user.getEmail() + "%");
            }
            if (user.getPhone() != null && user.getPhone().length() > 0) {
                criteria.andPhoneLike("%" + user.getPhone() + "%");
            }
            if (user.getWechat() != null && user.getWechat().length() > 0) {
                criteria.andWechatLike("%" + user.getWechat() + "%");
            }
            if (user.getSchool() != null && user.getSchool().length() > 0) {
                criteria.andSchoolLike("%" + user.getSchool() + "%");
            }

        }

        Page<User> page = (Page<User>) userMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    //	 TODO  未完成登录书写，测试登录功能，还需要修改一部分内容
    @Override
    public Result login(User user) {
        Result result = null;
        //判断用户名是否为null
        if (!StringUtils.isEmpty(user.getName())) {
            //判断用户是否存在
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andNameEqualTo(user.getName());
            List<User> users = userMapper.selectByExample(userExample);
            if (users != null && users.size() > 0) {
                User selectUser = users.get(0);
                if (selectUser.getPassword().equals(MD5Utils.md5(user.getPassword()))) { //密码是否需要进行加密
                    result = new Result(true, "登录成功", selectUser);
                } else {
                    result = new Result(false, "密码错误");
                }
            } else {
                result = new Result(false, "指定用户不存在");
            }
            //判断密码是否正确（加密）
        } else {
            result = new Result(false, "非法参数");
        }
        return result;
    }

    @Override
    public Result register(User user) {
        Result result = null;
        //校验user是否为null
        if(user != null) {
            //校验用户名密码是否存在
            if(!StringUtils.isEmpty(user.getName()) && !StringUtils.isEmpty(user.getPassword())) {
                //检验用户名是否已经存在
                UserExample userExample = new UserExample();
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andNameEqualTo(user.getName());
                List<User> users = userMapper.selectByExample(userExample);
                if(users == null || users.size() < 1) {
                    //添加id
                    user.setUuid(UUID.randomUUID().toString());
                    //修改密码
                    user.setPassword(MD5Utils.md5(user.getPassword()));

                    userMapper.insert(user);

                    result = new Result(true,"注册成功",user);
                }else {
                    result = new Result(false,"用户名已存在");
                }
            }else {
                result = new Result(false,"用户名或密码为null");
            }
        }else {
            result = new Result(false,"参数不能为null");
        }
        return result;
    }

}
