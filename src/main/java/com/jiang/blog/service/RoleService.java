package com.jiang.blog.service;

import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.pojo.Article;

public interface RoleService {

    PageInfo queryManyRole(Integer offset, Integer limit);
}
