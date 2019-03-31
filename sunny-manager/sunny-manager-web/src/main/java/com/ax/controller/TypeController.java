package com.ax.controller;

import com.ax.entity.Result;
import com.ax.pojogroup.Type;
import com.ax.pojo.TbType;
import com.ax.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;


    @RequestMapping("/findType")
    @ResponseBody
    public Result findType(TbType tbType) {
        Result result = null;
        try {
            List<TbType> type = typeService.findType(tbType.getParentId());
            result = new Result(true, "查询成功", type);
        } catch (Exception e) {
            result = new Result(false, "查询失败:异常");
        }
        return result;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(TbType tbType) {
        Result result = null;
        try {
            result = typeService.add(tbType);
        } catch (Exception e) {
            result = new Result(false, "添加失败:异常");
        }
        return result;
    }


    @RequestMapping("/findAll")
    @ResponseBody
    public Result findAll() {
        Result result = null;
        try {
            List<Type> all = typeService.findAll();
            result = new Result(true, "查询成功", all);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败");
        }
        return result;
    }


    /**
     * 根据之类id 查询父级 Type
     */
    @RequestMapping("/findParentType")
    @ResponseBody
    public Result findParentType(Long id) {
        Result result = null;
        try {
            TbType type = typeService.findParentType(id);
            result = new Result(true, "查询成功", type);
        } catch (Exception e) {
            result = new Result(false, "查询失败" + e.getMessage());
        }

        return result;
    }
}
