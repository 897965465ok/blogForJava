package com.jiang.blog.service;

import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.pojo.Menu;

import java.util.List;

public interface MenuService {


    PageInfo queryManyMenu(Integer offset, Integer limit);

    Menu queryOneMenu(Integer id);

    Integer updateOneMenu(Menu menu);

    List getRouter();
}
