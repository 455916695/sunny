package com.ax.controller;

import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbUser;
import com.ax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    //TODO 校验验证码功能,暂时未完成
    @RequestMapping("check")
    @ResponseBody
    public Result check() {

        return null;
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result login(TbUser user, HttpServletRequest request) {
        Result result = null;
        try {
            check();
            result = userService.login(user);
        } catch (Exception e) {
            e.printStackTrace();
            // 此处需要日志
            result = new Result(false, "登陆失败:系统异常");
        }
        if (result.isFlag()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", result.getData());
            user = (TbUser) result.getData();
            user.setPassword("");
        }
        return result;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Result register(TbUser user) {
        Result result = null;
        try {
            result = userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "注册失败:系统出现异常");
            //此处需要添加日志
        }
        return result;
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbUser> findAll() {
        return userService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    @ResponseBody
    public PageResult findPage(int page, int rows) {
        return userService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param user
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result add(TbUser user) {
        try {
            userService.add(user);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(TbUser user) {
        try {
            userService.update(user);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     * 根据用户id查询用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    @ResponseBody
    public Result findOne(@RequestParam(defaultValue = "0") Long id, HttpServletRequest request) {
        Result result = null;
        try {
            if (id == null || id == 0) {
                TbUser user = (TbUser) request.getSession().getAttribute("user");
                id = user.getId();
            }
            result = userService.findOne(id);
        } catch (Exception e) {
            result = new Result(false, "查询失败:系统异常");
        }
        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long[] ids) {
        try {
            userService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param user
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public PageResult search(TbUser user, int page, int rows) {
        return userService.findPage(user, page, rows);
    }

}
