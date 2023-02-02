package com.jiang.blog.service;

import com.jiang.blog.model.VO.UserVO;
import com.jiang.blog.model.pojo.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserService  {


    Integer register(User user);

    Map userLogin(String account, String password);

    UserVO queryManyUser(Integer offset, Integer limit);

    int deleteManyUser(ArrayList<String> ids);
}
