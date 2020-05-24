package com.cn.blog.service.impl;


import com.cn.blog.pojo.User;
import com.cn.blog.repository.UserRepository;
import com.cn.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username,password);
    }
}
