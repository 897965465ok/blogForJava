package com.jiang.blog.service.impl;
import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.dao.ExtendDao.ExtendMenuMapper;
import com.jiang.blog.model.dao.ExtendDao.ExtendUserMapper;
import com.jiang.blog.model.pojo.LoginUser;
import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.UserService;
import com.jiang.blog.utils.JwtUtil;
import com.jiang.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    ExtendUserMapper userMapper;

    @Autowired
    ExtendMenuMapper menuMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;


    @Override
    public Integer register(String account, String password) {
        Integer exists = userMapper.userExists(account);
        if (exists == 1) {
            throw new BlogException(BlogExceptionEnum.USER_EXISTS);
        } else {
            User user = new User();
            user.setEmail(account);
            user.setName(account);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


            password = bCryptPasswordEncoder.encode(password);

            /* password = CryptUtils.GeneratePassword(password, 12);*/

            user.setPassword(password);
            user.setCreatedAt(new Date());
            return userMapper.insert(user);
        }
    }

    @Override
    public Map userLogin(String account, String password) {


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, password);

        //authenticate 会调用 loadUserByUsername
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);


        if (Objects.isNull(authenticate)) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_EXISTS);
        }
        /*authenticate.getPrincipal() 会返回loadUserByUsername的返回值*/
        LoginUser loginUser = (LoginUser) (authenticate.getPrincipal());

        String userId = loginUser.getUser().getId().toString();


        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap();
        map.put("token", jwt);

        //5系统用户相关所有信息放入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        return map;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 重数据库查询id
        User user = userMapper.selectByUserName(username);

        if (user == null) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_EXISTS);
        }

        List<String> permissions = menuMapper.selectPermsByUserId(user.getId());

        return new LoginUser(user, permissions);
    }
}
