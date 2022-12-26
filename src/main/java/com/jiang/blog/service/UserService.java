package com.jiang.blog.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface UserService {


    Integer register(String account, String password);

    Map userLogin(String account, String password);

    PageInfo queryManyUser(Integer offset, Integer limit);
}
