package com.jiang.blog.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.blog.model.VO.UserTableHeader;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.model.pojo.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService  extends IService<User> {


    int userUpdate(User user);

    Integer register(User user);

    SaTokenInfo userLogin(String account, String password);

    UserTableHeader queryUserTableHeader();

    List queryManyUser(Integer offset, Integer limit);

    int deleteManyUser(ArrayList<String> ids);

    User selectByUserName(String account);

    Object getInfo();

    List<Role> queryRolesByUserId(User user);
}
