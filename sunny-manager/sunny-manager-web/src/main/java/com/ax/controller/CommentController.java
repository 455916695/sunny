package com.ax.controller;

import java.util.List;

import com.ax.entity.Comment;
import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbComment;
import com.ax.service.CommentService;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbComment> findAll() {
        return commentService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    @ResponseBody
    public PageResult findPage(int page, int rows) {
        return commentService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param comment
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result add(Comment comment) {
        try {
            commentService.add(comment);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param comment
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(Comment comment) {
        try {
            commentService.update(comment);
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
            List list = commentService.findOne(id);
            result = new Result(true, "查询成功", list);
        } catch (Exception e) {
            result = new Result(false, "查询失败");
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
            commentService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param comment
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public Result search(TbComment comment, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Result result = null;
        try {
            result = commentService.findPage(comment, pageNum, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败:异常");
        }

        return result;
    }

}
