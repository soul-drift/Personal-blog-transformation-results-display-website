package com.lr.blog.web.admin;

import com.lr.blog.po.User;
import com.lr.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")//全局的路径
public class LoginController {       //登录的web控制器

    @Autowired
    private UserService userService;    //根据用户名和密码是否能找到相应的对象user

    @GetMapping
    public String loginPage() {      //跳转到登录页面
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,          //登录界面提交的时候传用户名和密码
                        HttpSession session,                    //查到的用户名和密码对象
                        RedirectAttributes attributes) {        //用这个参数接收redirect返回的参数
        User user = userService.checkUser(username, password);  //传过来
        if (user != null) {                                     //如果user不为空，密码和用户名正确
            user.setPassword(null);                             //不要把密码传到前面去
            session.setAttribute("user",user);               //查到的用户对象放到session里面来，作为参数传递过来
            return "admin/index";                               //转到登录后的首页面
        } else {                                                //用户名和密码不对返回到登录页面
            attributes.addFlashAttribute("message", "用户名和密码错误");    //密码错误就给前端页面一个提示
            return "redirect:/admin";                           //redirect重定向到这个目录下
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {                 //登出，注销当前登录的用户
        session.removeAttribute("user");                     //清空session的东西清空，user拿掉
        return "redirect:/admin";                               //redirect重定向到这个目录下
    }
}
