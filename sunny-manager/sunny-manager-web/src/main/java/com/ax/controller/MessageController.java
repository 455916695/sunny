package com.ax.controller;

import com.ax.entity.Result;
import com.ax.pojo.TbImage;
import com.ax.pojo.TbMessage;
import com.ax.pojo.TbUser;
import com.ax.pojogroup.Message;
import com.ax.pojogroup.User;
import com.ax.service.ImageService;
import com.ax.service.MessageService;
import com.ax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;


    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;


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

//=======我是神奇的分界线========================================================================================================

    @RequestMapping("/addMessage")
    @ResponseBody
    public Result addMessage(TbMessage message) {
        Result result = null;
        try {
            messageService.addMessage(message);
            result = new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "添加失败" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/findMessage")
    @ResponseBody
    public Result findMessage(Long id, Long otherId, Long goodsId) {  //
        Result result = null;
        try {
            List<TbMessage> message = messageService.findMessage(id, otherId, goodsId);

            //修改消息状态 为已阅读
            messageService.updateMessageStatusForReaded(message, id);

            result = new Result(true, "查询成功", message);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败" + e.getMessage());
        }
        return result;
    }

    @RequestMapping("/findNewMessage")
    @ResponseBody
    public Result findNewMessage(Long reId) {

        Result result = null;
        try {
            List<TbMessage> newMessage = messageService.findNewMessage(reId);

            List<Message> messages = new ArrayList(); //创建返回的集合
            for (int i = 0; i < newMessage.size(); i++) {

                Message tempMessage = new Message();

                TbMessage message = newMessage.get(i);  //查询消息

                TbUser one = userService.findOne(message.getSendId());  //查询发送者
                List<TbImage> byKindId = imageService.findByKindId(one.getId()); //查询发送者信息


                User user = new User();
                user.setUser(one);
                if (byKindId != null && byKindId.size() >= 1)
                    user.setImage(byKindId.get(0));

                List<TbImage> imagesList = imageService.findByKindId(message.getGoodsId()); //查询商品图片
                if (imagesList != null && imagesList.size() >= 1)
                    tempMessage.setImage(imagesList.get(0));

                tempMessage.setMessage(message);
                tempMessage.setSendUser(user);

                messages.add(tempMessage);
            }
            result = new Result(true, "查询成功", messages);
        } catch (Exception e) {
            result = new Result(false, "查询失败：系统异常" + e.getMessage());
        }
        return result;
    }


    /**
     * 查询指定用户是否存在未阅读的消息
     *
     * @Parame userId  用户id
     */
    @RequestMapping("/findUnreadMessage")
    @ResponseBody
    public Result findUnreadMessage(Long userId) {
        Result result = null;
        try {
            boolean isHave = messageService.findUnreadMessage(userId);
            if (isHave) {
                result = new Result(true, "查询成功,该用户存在未阅读消息");
            } else {
                result = new Result(false, "查询成功,该用户不存在未阅读消息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, "查询失败:系统异常" + e.getMessage());
        }
        return result;
    }

}
