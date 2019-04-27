package com.ax.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.ax.entity.PageResult;
import com.ax.entity.Result;
import com.ax.pojo.TbImage;
import com.ax.pojo.TbUser;
import com.ax.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;


    @Value("${DEFAULT_IMAGE_PATH}") //    /home/houry/images/test.bmp
    private String DEFAULT_IMAGE_PATH;
    @Value("${IMAGE_SERVER_PATH}")
    private String IMAGE_SERVER_PATH; //  /home/houry/

    //TODO 此处只是测试，已经初步实现，从D:盘中读取图片

    /**
     * 图片回显测试
     */
    @RequestMapping("/showImg")
    public void showImg(HttpServletResponse response, String imgPath) throws IOException {
        if (StringUtils.isEmpty(imgPath)) {
            imgPath = DEFAULT_IMAGE_PATH;
        }
        File file = new File(imgPath);
        if (!file.exists()) {
            file = new File(DEFAULT_IMAGE_PATH);
        }
        FileInputStream inputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        //我们就每次读写10K,我们的文件小，这个就已经够用了
        byte[] b = new byte[1024 * 10];

        int n = 0;

        //读写文件,-1标识为空
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }

        //3、关闭流
        inputStream.close();
        outputStream.close();
    }

    //TODO 此处测试，图片上传，如果不能实现，请将此处代码进行删除
    @RequestMapping("/imgUpload")
    @ResponseBody
    public Result imgUpload(MultipartFile imgFile, @RequestParam(defaultValue = "0") int kind, TbUser user, HttpServletRequest request) throws IOException {
        // 图片路径 安排   IMAGE_SERVER_PATH + 用户名 +"/类型/当前毫秒值.后缀"
        Result result = null;
        try {
            if (user == null || user.getId() == null || StringUtils.isEmpty(user.getUsername())) {
                user = (TbUser) request.getSession().getAttribute("user");
            }
            File file = imageService.upload(IMAGE_SERVER_PATH, imgFile.getOriginalFilename(), kind, user);
            imgFile.transferTo(file);  //只负责上传图片,不负责,将图片地址一些信息保存到数据库
//            imageService.add(new TbImage(new IdWorker().nextId(), kind, user.getId(), file.getPath()));
            result = new Result(true, "上传成功", file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "上传失败:上传异常");
        }
        return result;
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public List<TbImage> findAll() {
        return imageService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    @ResponseBody
    public PageResult findPage(int page, int rows) {
        return imageService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param image
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Result add(TbImage image) {
        try {
            imageService.add(image);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param image
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public Result update(TbImage image) {
        try {
            imageService.update(image);
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
    public TbImage findOne(Long id) {
        return imageService.findOne(id);
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
            imageService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param image
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public PageResult search(TbImage image, int page, int rows) {
        return imageService.findPage(image, page, rows);
    }


    /**
     * 上传图片地址
     */
    @RequestMapping("/addImageAddress")
    @ResponseBody
    public Result addImageAddress(String address, Long id, Integer kind) {
        Result result = null;
        try {
            imageService.addImage(address, id, kind);
            result = new Result(true, "添加成功");
        } catch (Exception e) {
            result = new Result(false, "添加失败:" + e.getMessage());
        }
        return result;
    }


    /**
     * 查询指定图片地址
     */
    @RequestMapping("/findImageAddress")
    @ResponseBody
    public Result findImageAddress(TbImage image) {
        Result result = null;
        try {
            TbImage img = imageService.findImageAddress(image);
            result = new Result(true, "查询成功", img);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败" + e.getMessage());
        }
        return result;
    }


}
