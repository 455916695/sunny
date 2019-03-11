package com.ax.controller;

import java.util.List;

import com.ax.entity.Goods;
import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbGoods;
import com.ax.pojo.TbUser;
import com.ax.service.GoodsService;
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
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbGoods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表  暂时不开放该接口
     *
     * @return
     */
//    @RequestMapping("/findPage")
//    @ResponseBody
//    public Result findPage(int page, int rows) {
//        return goodsService.findPage(page, rows);
//    }

    /**
     * 增加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add")   //TODO  此功能暂时开发到这里，可能存在一些bug
    @ResponseBody
    public Result add(Goods goods, HttpServletRequest request) {
        try {
            if (goods != null) {
                TbUser user = (TbUser) request.getSession().getAttribute("user");
                goodsService.add(goods, user);
                return new Result(true, "增加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "增加失败");
    }

    /**
     * 修改
     *
     * @param goods
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(Goods goods) {
        try {
            goodsService.update(goods);
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
            Goods one = goodsService.findOne(id);
            result = new Result(true, "查询成功", one);
        } catch (Exception e) {
            e.printStackTrace();
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
            goodsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param goods
     * @param psgeNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public Result search(TbGoods goods, @RequestParam(defaultValue = "1") int psgeNum, @RequestParam(defaultValue = "10") int pageSize) {
       Result result = null;
       try {
           result =  goodsService.findPage(goods, psgeNum, pageSize);
       }catch (Exception e){
           e.printStackTrace();
           result = new Result(false,"查询失败:异常");
       }
       return result;
    }

}
