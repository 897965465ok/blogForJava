package com.jiang.blog.service.impl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.dao.UserMapper;
import com.jiang.blog.model.pojo.Article;
import com.jiang.blog.model.pojo.LoginUser;
import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.UserService;
import com.jiang.blog.utils.JwtUtil;
import com.jiang.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl  implements  UserDetailsService ,UserService {
    @Autowired
    UserMapper userMapper;


    @Autowired
    MenuMapper menuMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisCache redisCache;




    @Override
    public PageInfo queryManyUser(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit);
        List<User> userList = userMapper.queryManyUser();

        PageInfo pageinfo = new PageInfo<>(userList);
        return pageinfo;
    }



    @Override
    public Integer register(String account, String password) {
        Integer exists = userMapper.userExists(account);
        if (exists == 1) {
            throw new BlogException(BlogExceptionEnum.USER_EXISTS);
        } else {
            User user = new User();
            user.setEmail(account);
            user.setUserName(account);
            user.setNickName(account);

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


            password = bCryptPasswordEncoder.encode(password);

            /* password = CryptUtils.GeneratePassword(password, 12);*/

            user.setPassword(password);
            user.setCreateTime(new Date());



            return    userMapper.insert(user);
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

        String userId = loginUser.getUser().getUserId().toString();


        String jwt = JwtUtil.createJWT(userId);
        Map<String, String> map = new HashMap();
        map.put("token", jwt);

        //5系统用户相关所有信息放入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        return map;
    }


/*    public  void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getUserId();
        redisCache.deleteObject("login:"+userid);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 重数据库查询id
        User user = userMapper.selectByUserName(username);

        if (user == null) {
            throw new BlogException(BlogExceptionEnum.USER_NOT_EXISTS);
        }

        // 重数据库拿字符 比如System:article:read
        List<String> permissions = menuMapper.selectPermsByUserId(user.getUserId());

        return new LoginUser(user, permissions);
    }


}
