package com.ax.controller;

import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbOrder;
import com.ax.pojo.TbUser;
import com.ax.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 返回全部订单
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbOrder> findAll() {
        return orderService.findAll();
    }


    /**
     * 增加
     *
     * @param order
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result add(TbOrder order, HttpServletRequest request) {
        try {
            if (order.getBuyerId() == null) {  //此处认为order不会为null 为前提
                TbUser user = (TbUser) request.getSession().getAttribute("user");
                order.setBuyerId(user.getId());
            }
            orderService.add(order);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param order
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(TbOrder order) {
        try {
            orderService.update(order);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    @ResponseBody
    public Result findOne(Long id) {
        Result result = null;
        try {
            TbOrder one = orderService.findOne(id);
            result = new Result(true, "查询成功", one);
        } catch (Exception e) {
            result = new Result(false, "查询失败:异常");
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
            orderService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param order
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public PageResult search(TbOrder order, int page, int rows) {
        return orderService.findPage(order, page, rows);
    }


    //TODO  准备开发 订单管理

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    @ResponseBody
    public Result findPage(TbOrder order, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize, HttpServletRequest request) {
        Result result = null;
        try {
            TbUser user = (TbUser) request.getSession().getAttribute("user");
            result = orderService.findPage(pageNum, pageSize, user, order);
        } catch (Exception e) {
            result = new Result(false, "查询失败:异常");
        }
        return result;
    }

}
