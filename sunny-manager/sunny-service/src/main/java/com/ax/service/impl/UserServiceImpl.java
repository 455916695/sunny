package com.ax.service.impl;

import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.mapper.TbUserMapper;
import com.ax.pojo.TbUser;
import com.ax.pojo.TbUserExample;
import com.ax.service.UserService;
import com.ax.util.IdWorker;
import com.ax.util.MD5Utils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbUser> findAll() {
        return userMapper.selectByExample(null);
    }


    //	 TODO  未完成登录书写，测试登录功能，还需要修改一部分内容
    @Override
    public Result login(TbUser user) {
        Result result = null;
        //判断用户名是否为null
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            result = new Result(false, "登陆失败:用户信息异常");
        } else {
            //判断用户是否存在
            TbUserExample tue = new TbUserExample();
            TbUserExample.Criteria criteria = tue.createCriteria();
            criteria.andUsernameEqualTo(user.getUsername());
            List<TbUser> tbUsers = userMapper.selectByExample(tue);
            if (tbUsers == null || tbUsers.size() < 1) {
                result = new Result(false, "登陆失败:指定用户不存在");
            } else {
                //判断密码是否正确
                if (MD5Utils.md5(user.getPassword()).equals(tbUsers.get(0).getPassword())) {
                    result = new Result(true, "登陆成功", tbUsers.get(0));
                } else {
                    result = new Result(false, "登陆失败:密码不正确");
                }
            }
        }
        return result;
    }


    @Override
    public Result register(TbUser user) {
        Result result = null;
        //判断用户参数
        if (user == null || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            //参数为空
            result = new Result(false, "注册失败:用户信息异常");
        } else {
            //校验用户是否已近存在
            TbUserExample tbe = new TbUserExample();
            TbUserExample.Criteria criteria = tbe.createCriteria();
            criteria.andUsernameEqualTo(user.getUsername());
            List<TbUser> tbUsers = userMapper.selectByExample(tbe);  //TODO 这里
            if (tbUsers != null && tbUsers.size() > 0) {
                result = new Result(false, "注册失败:用户已存在");
            } else {
                user.setId(new IdWorker().nextId());
                user.setPassword(MD5Utils.md5(user.getPassword()));
                userMapper.insert(user);
                result = new Result(true, "注册成功");
            }
        }
        return result;
    }


    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbUser user) {
        userMapper.insert(user);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbUser user) {
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbUser findOne(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            userMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TbUser user, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();

        if (user != null) {
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

        Page<TbUser> page = (Page<TbUser>) userMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
