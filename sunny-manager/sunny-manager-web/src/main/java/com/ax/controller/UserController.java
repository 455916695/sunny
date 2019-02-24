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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	public Result login(User user, HttpServletRequest request) {
		Result result = new Result(false,"非法参数");
		if(user != null) {
			result = userService.login(user);
			if(result.isFlag()) {
				HttpSession session = request.getSession();
				session.setAttribute("user",result.getData());
				user = (User) result.getData();
				user.setPassword("");
			}
		}
		return result;
	}

	@RequestMapping("/register")
	@ResponseBody
	public Result register(User user) {
		Result result = new Result(false,"非法参数");
		if(user != null){
			result = userService.register(user);
		}
		return result;
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
