package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.pojo.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    PageInfo queryManyMenu(Integer offset, Integer limit);

    Menu queryOneMenu(Integer id);


    Integer updateOneMenu(Menu menu);

    List getRouter();

    boolean deleteMenuByOne(String id);
}
