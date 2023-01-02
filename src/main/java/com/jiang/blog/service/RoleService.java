package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.pojo.Article;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.model.pojo.User;


// 使用Iserver接口
public interface RoleService extends IService<Role> {

    PageInfo queryManyRole(Integer offset, Integer limit);
}
