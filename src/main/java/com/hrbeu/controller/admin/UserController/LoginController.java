package com.hrbeu.controller.admin.UserController;

import com.hrbeu.pojo.User;
import com.hrbeu.service.admin.UserService;
import com.hrbeu.utils.RedisUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ModelAndView login(@Param("username")String username,@Param("password")String password,HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        User user = userService.checkUser(username, password);
        if(user==null){
            model.addObject("errMsg","用户名不存在或密码错误");
            model.setViewName("/admin/login");
            return model;
        }else {
            if(user.getAuthority()!=2){
                model.addObject("errMsg","当前用户不是管理员用户，不能进入后台管理");
                model.setViewName("/admin/login");
                return model;
            }else {
                user.setPassword(null);
                HttpSession session = request.getSession();
                session.setAttribute("user",user);
            }

        }
        model.setViewName("redirect:/admin/index");
        return model;
    }
    //登出
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
