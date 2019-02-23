package com.ax.controller;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.User;
import com.ax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;


	//TODO 测试拦截功能
	/**
	 *  登录
	 * */
	@RequestMapping("/login")
	@ResponseBody
	public Result login(User user) {
		return new Result(true,"登录成功");
	}
	@RequestMapping("/register")
	@ResponseBody
	public Result register(User user) {
		return new Result(true,"注册成功");
	}



	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<User> findAll(){
		return  userService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	@ResponseBody
	public PageResult findPage(int page, int rows){
		return userService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param user
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@RequestBody User user){
		try {
			userService.add(user);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@RequestBody User user){
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
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	@ResponseBody
	public User findOne(String id){
		return userService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Result delete(String [] ids){
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
	 * @param user
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public PageResult search( User user, int page, int rows  ){
		return userService.findPage(user, page, rows);		
	}
	
}
