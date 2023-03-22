package com.jiang.blog.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.VO.UserTableHeader;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.dao.UserMapper;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.UserService;
import com.jiang.blog.utils.Crypt;
import com.jiang.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;


    @Autowired
    MenuMapper menuMapper;


    @Autowired
    RedisCache redisCache;


    @Override
    @CachePut(value = "queryRolesByUserId")
    public List<Role> queryRolesByUserId(User user) {
        return      userMapper.queryRolesByUserId(user.getUserId());
    }


    @Override
    @CachePut(value = "queryUserTableHeader")
    public UserTableHeader queryUserTableHeader() {
        return new UserTableHeader();
    }


    @Override
    @CachePut(value = "queryManyUser")
    public List queryManyUser(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit);
        List<User> userList = userMapper.queryManyUser();
        return userList;
    }


    @Override
    public int deleteManyUser(ArrayList<String> ids) {
        int result = userMapper.deleteBatchIds(ids);
        return result;
    }

    @Override
    public int userUpdate(User user) {
        int result = userMapper.updateById(user);
        return result;
    }

    @Override
    public Integer register(User user) {
        Integer exists = userMapper.userExists(user.getUserName());
        if (exists == 1) {
            throw new BlogException(BlogExceptionEnum.USER_EXISTS);
        } else {
            user.setPassword(Crypt.encrypt(user.getPassword()));
            return userMapper.insert(user);
        }
    }


    @Override
    public SaTokenInfo userLogin(String account, String password) {
        User user = userMapper.selectByUserName(account);
        if (!Objects.isNull(user) && password.equals(Crypt.decrypt(user.getPassword()))) {
            StpUtil.login(user.getUserId());
            // 第2步，获取 Token  相关参数
            return StpUtil.getTokenInfo();

            //5系统用户相关所有信息放入redis
            // redisCache.setCacheObject("login");
        }
        return null;

    }

    @Override
    public User selectByUserName(String account) {
        User user = userMapper.selectByUserName(account);
        if (!Objects.isNull(user)) {
            return user;
            //5系统用户相关所有信息放入redis
            // redisCache.setCacheObject("login");
        }
        return null;

    }




/*    public  void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getUserId();
        redisCache.deleteObject("login:"+userid);
    }*/


    @Override
    public Object getInfo() {
        // 获取当前登录用户基础信息


        // 获取当前用户 权限字符
        // 获取用户 角色
        return null;
    }



}
