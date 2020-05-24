package com.cn.blog.controller;

import com.cn.blog.handle.MD5util;
import com.cn.blog.pojo.User;
import com.cn.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes redirectAttributes){
        User user = userService.checkUser(username, MD5util.code(password));
        user.setPassword(null);
        if(user!=null){
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            redirectAttributes.addAttribute("message","用户名和密码错误");
            return "redirect:/admin";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
