package com.ax.controller;
import java.io.*;
import java.util.List;


import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.GoodsImg;
import com.ax.service.ImgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/img")
public class ImgController {

	@Autowired
	private ImgService imgService;


	@Value("${DEFAULT.IMAGE.PATH}") //TODO 此处测试从配置文件中获取常量数据，已经初步实现从配置文件中获取指定常量值
	private  String DEFAULT_IMAGE_PATH;

	//TODO 此处只是测试，已经初步实现，从D:盘中读取图片
	/**
	 * 图片回显测试
	 * */
	@RequestMapping("/showImg")
	public void  showImg(HttpServletResponse response,String imgPath) throws IOException {
		if(StringUtils.isEmpty(imgPath)) {
			imgPath = DEFAULT_IMAGE_PATH;
		}
		File file = new File(imgPath);
		if(!file.exists()) {
			file = new File(DEFAULT_IMAGE_PATH);
		}
		FileInputStream inputStream = new FileInputStream(file);
		ServletOutputStream outputStream = response.getOutputStream();
		//我们就每次读写10K,我们的文件小，这个就已经够用了
		byte[] b = new byte[1024*10];

		int n = 0 ;

		//读写文件,-1标识为空
		while( (n = inputStream.read(b) ) != -1 ) {
			outputStream.write(b, 0, n);
		}

		//3、关闭流
		inputStream.close();
		outputStream.close();
	}

	//TODO 此处测试，图片上传，如果不能实现，请将此处代码进行删除
	@RequestMapping("/imgUpload")
	@ResponseBody
	public String imgUpload(MultipartFile imgFile) throws IOException {

		String name = imgFile.getOriginalFilename();
		name = name.replace(".", "1.");
		File file = new File("D:\\file\\photo\\"+name);
//		FileOutputStream fileOutputStream = new FileOutputStream(file);
//		fileOutputStream.write(imgFile.getBytes());
//		fileOutputStream.close();

		imgFile.transferTo(file);
		return file.getPath();
	}

	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public List<GoodsImg> findAll(){
		return imgService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	@ResponseBody
	public PageResult findPage(int page, int rows){
		return imgService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param img
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Result add(@RequestBody GoodsImg img){ //注意，使用@RequestBody 出现400 的与原因是因为注入对象中存在date或者int,而前台传递的Strig类型所以报错
		try {
			imgService.add(img);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param img
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Result update(@RequestBody GoodsImg img){
		try {
			imgService.update(img);
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
	public GoodsImg findOne(String id){
		return imgService.findOne(id);		
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
			imgService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param img
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	@ResponseBody
	public PageResult search(@RequestBody GoodsImg img, int page, int rows  ){
		return imgService.findPage(img, page, rows);		
	}
	
}
