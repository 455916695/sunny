package com.ax.controller;

import com.ax.entity.Result;
import com.ax.pojo.TbMessage;
import com.ax.pojo.TbUser;
import com.ax.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping("/add")
    public Result add(TbMessage message, HttpServletRequest request) {
        TbUser user = (TbUser) request.getSession().getAttribute("user");
        try {
            return messageService.add(user, message);
        } catch (Exception e) {
            return new Result(false, "添加对话失败!");
        }
    }

    public Result get(HttpServletRequest request) {
        TbUser user = (TbUser) request.getSession().getAttribute("user");
        return messageService.get(user);
    }
}
