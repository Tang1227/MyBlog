package com.cn.blog.service;
import com.cn.blog.pojo.User;



public interface UserService {

   User checkUser(String username, String password);
}
