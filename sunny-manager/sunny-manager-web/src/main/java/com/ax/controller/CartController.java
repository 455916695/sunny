package com.ax.controller;

import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbCart;
import com.ax.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbCart> findAll() {
        return cartService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    @ResponseBody
    public PageResult findPage(int page, int rows) {
        return cartService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param cart
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result add(TbCart cart) {
        Result result = null;
        try {
            // 传入参数 查询 指定商品是否已经存在与购物车中
            boolean exist = cartService.findCountByGoodsId(cart.getBuyerId(),cart.getGoodsId());
            if (!exist) {
                cartService.add(cart);
                result = new Result(true, "增加成功");
            } else {
                result = new Result(false, "购物车添加失败，已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "增加失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 修改
     *
     * @param cart
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(TbCart cart) {
        try {
            cartService.update(cart);
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
            result = cartService.findOne(id);
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
            cartService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param cart
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public Result search(TbCart cart, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {

        Result result = null;
        try {
            result = cartService.findPage(cart, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败:异常" + e.getMessage());
        }
        return result;
    }

}
