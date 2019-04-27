package com.ax.controller;

import java.util.ArrayList;
import java.util.List;

import com.ax.entity.Goods;
import com.ax.entity.PageResult;
import com.ax.pojo.*;
import com.ax.pojogroup.NewGoods;
import com.ax.entity.Result;
import com.ax.service.*;
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

    @Autowired
    private ContentService contentService;  //评论service

    @Autowired
    private ImageService imageService; //图片 image

    @Autowired
    private TypeService typeService;  //分类查询

    @Autowired
    private OrderService orderService; //查询订单

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
     * 增加
     *
     * @param goods
     * @return
     */
    @RequestMapping("/add")
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
            return new Result(false, "删除失败"+e.getMessage());
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
            result = goodsService.findPage(goods, psgeNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败:异常");
        }
        return result;
    }


    //=====分界线================我是神奇的分界线===================================================================================================
    @RequestMapping("/addGoods")
    @ResponseBody
    public Result addGoods(NewGoods goods,String address) {

        Result result = null;
        try {
            //如果添加成功，返回描述的id
            TbContent add = contentService.add(goods.getContent());
            goods.getGoods().setContentId(add.getId());
            TbGoods tbGoods = goodsService.addGoods(goods.getGoods());

            //添加商品图片
            imageService.addImage(address,tbGoods.getId(),2);

            result = new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "添加失败");
        }
        return result;
    }

    /**
     * 查询指定商品
     */
    @RequestMapping("/findGoodsById")
    @ResponseBody
    public Result findGoodsById(Long id) {
        Result result = null;
        NewGoods goods = null;
        try {
//              TbGoods goods;
//              TbType type;          //暂时没有写，如果需要可以在这里添加
//              TbContent content;
//              List<TbImage> imageList;
            goods = new NewGoods();      //创建组合实体
            // 查询商品本身
            TbGoods tbGoods = goodsService.findOneTbGoods(id);  //查询商品信息
            TbContent content = contentService.findOne(tbGoods.getContentId());  //查询 详情信息
            List<TbImage> imagesList = imageService.findByKindId(tbGoods.getId()); //查询 商品图片

            goods.setGoods(tbGoods);
            goods.setContent(content);
            goods.setImageList(imagesList);

            result = new Result(true, "商品查询成功", goods);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "商品查询失败：系统异常");
        }

        return result;
    }

    /**
     * 粗略查询全部商品信息(问题是否需要带图片)
     */
    @RequestMapping("/findAllTbGoods")
    @ResponseBody
    public Result findAllTbGoods() {
        Result result = null;
        try {
            List<TbGoods> allTbGoods = goodsService.findAllTbGoods();
            result = new Result(true, "查询成功", allTbGoods);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败：系统异常");
        }
        return result;
    }

    /**
     * 条件分页查询,查询商品
     */
    @RequestMapping("/newSearch")
    @ResponseBody
    public Result newSearch(TbGoods goods, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Result result = null;
        //此处存在着大片的处理
        //创建相应的对象
        List<NewGoods> list = new ArrayList<>();   //创建结果 集合
        try {
            PageResult search = goodsService.search(goods, pageNum, pageSize);  //查询商品信息

            List<TbGoods> rows = search.getRows();    //遍历商品，并丰富其他信息
            if (search.getRows() != null)
                for (TbGoods tbGoods : rows) {

                    //封装成新的商品对象
                    NewGoods newGoods = new NewGoods();

                    //查询图片                //这里假设他们只查询 商品基本信息和商品图片
                    List<TbImage> imagesList = imageService.findByKindId(tbGoods.getId());

                    newGoods.setGoods(tbGoods);
                    newGoods.setImageList(imagesList);

                    list.add(newGoods);
                }

            search.setRows(list);
            result = new Result(true, "查询成功", search);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败:系统异常"+e.getMessage());
        }
        return result;
    }


    /**
     * 查询 已经购买的商品
     * <p>
     * *
     */
    @RequestMapping("/findBoughtGoods")
    @ResponseBody
    public Result findBoughtGoods(TbOrder tbOrder) {
        Result result = null;
        try {
            //同过用户查询订单，通过订单里的商品信息，查询商品信息返回
            List<TbOrder> orders = orderService.findOrderByBuyerId(tbOrder);  //查询订单

            List<NewGoods> goodsList = new ArrayList();

            if (orders != null && orders.size() > 0)
                for (TbOrder order : orders) {

                    NewGoods newGoods = new NewGoods();

                    TbGoods oneTbGoods = goodsService.findOneTbGoods(order.getGoodsId());
                    List<TbImage> byKindId = imageService.findByKindId(oneTbGoods.getId());

                    newGoods.setGoods(oneTbGoods);
                    newGoods.setImageList(byKindId);

                    goodsList.add(newGoods);
                }

            result = new Result(true, "查询成功", goodsList);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败:系统异常" + e.getMessage());
        }
        return result;
    }


    /**
     * 更新问题：留到明天解决
     * */


}
