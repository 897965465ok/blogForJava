package com.jiang.blog.service.impl;


import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.dao.UserMapper;
import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.UserService;
import com.jiang.blog.utils.CryptUtils;
import com.jiang.blog.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Integer register(String account, String password) {
        Integer exists = userMapper.userExists(account);
        if (exists == 1) {
            throw new BlogException(BlogExceptionEnum.USER_EXISTS);
        } else {
            User user = new User();
            user.setEmail(account);
            user.setName(account);
            password = CryptUtils.GeneratePassword(password, 12);
            user.setPassword(password);
            user.setCreatedAt(new Date());
            return userMapper.insertSelective(user);
        }
    }

    @Override
    public Map userLogin(String account, String password) {
        User user = userMapper.selectByUserName(account);
        if (user == null) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_EXISTS);
        }
        if (CryptUtils.verifyPassword(password, user.getPassword())) {

            // 发放token
            String token = JWTUtils.createToken(user);
            Map<String, Object> userInfo = new HashMap<>();
            user.setPassword(null);
            userInfo.put("user", user);
            userInfo.put("token", token);
            return userInfo;
        }
        throw new BlogException(BlogExceptionEnum.USER_NOT_EXISTS);

    }


}
