package com.jiang.blog.model.dao;

import com.jiang.blog.model.pojo.roleMenuKey;

public interface roleMenuMapper {
    int deleteByPrimaryKey(roleMenuKey key);

    int insert(roleMenuKey record);

    int insertSelective(roleMenuKey record);
}