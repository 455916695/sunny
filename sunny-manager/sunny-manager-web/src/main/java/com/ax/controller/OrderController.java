package com.ax.controller;

import java.util.ArrayList;
import java.util.List;

import com.ax.entity.Goods;
import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbGoods;
import com.ax.pojo.TbImage;
import com.ax.pojo.TbOrder;
import com.ax.pojo.TbUser;
import com.ax.pojogroup.NewGoods;
import com.ax.pojogroup.Order;
import com.ax.service.GoodsService;
import com.ax.service.ImageService;
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

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ImageService imageService;

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
    public Result findPage(TbOrder order, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Result result = null;
        try {
            PageResult pageResult = orderService.findPage(order, pageNum, pageSize);
            result = new Result(true, "查询成功", pageResult);
        } catch (Exception e) {
            result = new Result(false, "查询失败:异常");
        }
        return result;
    }

//==========================================================================================================

    /**
     * 添加订单
     */
    @RequestMapping("/addOrder")
    @ResponseBody
    public Result addOrder(TbOrder order) {
        Result result = null;
        try {
            orderService.addOrder(order);
            result = new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "添加失败：异常");
        }
        return result;
    }


    /**
     * 条件分页查询
     */
    @RequestMapping("/newSearch")
    @ResponseBody
    public Result newSearch(TbOrder order, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Result result = null;
        try {
            PageResult pageResult = orderService.newSearch(order, pageNum, pageSize);  //查询order本身

            List<Order> list = new ArrayList();  //创建 存放order 的集合
            List<TbOrder> rows = pageResult.getRows();  // 取出原有的TbOrder

//            循环遍历，封装order
            if (rows != null)
                for (TbOrder row : rows) {
                    Order newOrder = new Order();  //创建 order的组合实体类
//                      TbOrder order;  //订单本身
//                      TbUser buyer;   //买家
//                      TbUser seller;  //卖家
//                      TbGoods goods;  //商品
                    NewGoods newGoods = new NewGoods();   //封装商品信息
                    TbGoods goods = goodsService.findOneTbGoods(row.getGoodsId());
                    List<TbImage> imageList = imageService.findByKindId(goods.getId());
                    newGoods.setGoods(goods);
                    newGoods.setImageList(imageList);

                    newOrder.setOrder(row);           //封装order信息
                    newOrder.setGoods(newGoods);
                    list.add(newOrder);   //将数据添加到集合中
                }
            pageResult.setRows(list);

            result = new Result(true, "查询成功", pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败:系统异常" + e.getMessage());
        }
        return result;
    }


    /**
     * 订单支付后   状态为2                          //update()
     */
    @RequestMapping("/pay")
    @ResponseBody
    public Result pay(TbOrder order) {
        Result result = null;
        try {

            //查询商品是否还处于未被购买的状态  如果可以购买返回true
            boolean isCanBuy = goodsService.findGoodsIsCanBuy(order.getGoodsId());

            if (isCanBuy) {

                order.setStatus((byte) 2);
                orderService.update(order);

                //修改商品状态  2 表示被购买
                goodsService.updateStatusById(order.getGoodsId(), 2);
                result = new Result(true, "支付成功");

            } else {
                result = new Result(false, "该商品已被其他用户抢先付钱了");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "网络异常,请重新支付" + e.getMessage());
        }
        return result;
    }

    /**
     * 订单发货后   状态为3                                 //update()
     */
    @RequestMapping("/sendGoods")
    @ResponseBody
    public Result sendGoods(TbOrder order) {
        Result result = null;
        try {
            order.setStatus((byte) 3);
            orderService.update(order);
            result = new Result(true, "发货成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "网络异常,请重新支付" + e.getMessage());
        }
        return result;
    }


    /**
     * 订单确认收货   状态为4                                 //update()
     */
    @RequestMapping("/gotGoods")
    @ResponseBody
    public Result gotGoods(TbOrder order) {
        Result result = null;
        try {
            order.setStatus((byte) 4);
            orderService.update(order);
            result = new Result(true, "确认收货");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "系统异常,请重新确认收货" + e.getMessage());
        }
        return result;
    }

}
