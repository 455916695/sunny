package com.ax.interceptor;


import com.ax.entity.Result;
import com.ax.pojo.User;
import com.ax.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 登录拦截器，编写完此类，还需要在springmvc.xml文件中，进行配置
 */
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        请求处理前
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || StringUtils.isEmpty(user.getUuid())) {
//            将拦截原因返回给前台
//            重置response
            httpServletResponse.reset();
//            设置编码
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");

            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(JsonUtils.objectToJson(new Result(false,"未登录")));
            writer.flush();
            writer.close();
            return false; //false 表示拦截
        }
        return true; //false 表示拦截
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//    请求处理后
    }
}
