package com.ax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 自我尝试的方法
 */
@Controller
@RequestMapping("/file")
public class FileController {


    @RequestMapping("/fileUpload")
    @ResponseBody
    public String fileUpload(String filePath, MultipartFile uploadFile) {
        //此处省略参数校验
        String originalFilename = uploadFile.getOriginalFilename();

        String substring = originalFilename.substring(originalFilename.indexOf('.'));

        StringBuffer path = new StringBuffer(filePath);
        path.append("sunny").append(substring);

        File file = new File(path.toString());

        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toPath().toString();
    }


}
