package com.ax.controller;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.Integral;
import com.ax.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/integral")
public class IntegralController {

	@Autowired
	private IntegralService integralService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<Integral> findAll(){
		return integralService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return integralService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param integral
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Integral integral){
		try {
			integralService.add(integral);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param integral
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Integral integral){
		try {
			integralService.update(integral);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Integral findOne(String id){
		return integralService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String [] ids){
		try {
			integralService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param integral
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody Integral integral, int page, int rows  ){
		return integralService.findPage(integral, page, rows);		
	}
	
}
