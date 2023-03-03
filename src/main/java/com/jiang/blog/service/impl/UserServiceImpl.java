package com.jiang.blog.service.impl;
import com.github.pagehelper.PageHelper;
import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.VO.UserTableHeader;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.dao.UserMapper;
import com.jiang.blog.model.pojo.LoginUser;
import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.UserService;
import com.jiang.blog.utils.JwtUtil;
import com.jiang.blog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "queryUserTableHeader")
    public UserTableHeader queryUserTableHeader() {
        return new UserTableHeader();
    }



    @Override
    @Cacheable(value = "queryManyUser")
    public List queryManyUser(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit);
        List<User> userList = userMapper.queryManyUser();
        return userList;
    }


    @Override
    public int deleteManyUser(ArrayList<String> ids) {
        int result =   userMapper.deleteBatchIds(ids);
        return result ;
    }

    @Override
    public int userUpdate(User user) {
        int result =   userMapper.updateById(user);
        return result ;
    }

    @Override
    public Integer register(User user) {
        Integer exists = userMapper.userExists(user.getPassword());
        if (exists == 1) {
            throw new BlogException(BlogExceptionEnum.USER_EXISTS);
        } else {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));
            /* password = CryptUtils.GeneratePassword(password, 12);*/
          /*  user.setCreateTime(new Date());*/

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
