package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.blog.model.VO.UserTableHeader;
import com.jiang.blog.model.pojo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface UserService  extends IService<User> {


    int userUpdate(User user);

    Integer register(User user);

    Map userLogin(String account, String password);

    UserTableHeader queryUserTableHeader();

    List queryManyUser(Integer offset, Integer limit);

    int deleteManyUser(ArrayList<String> ids);

    Object getInfo();
}
