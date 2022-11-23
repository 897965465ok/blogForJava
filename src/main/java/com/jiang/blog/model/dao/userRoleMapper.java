package com.jiang.blog.model.dao;

import com.jiang.blog.model.pojo.userRoleKey;

public interface userRoleMapper {
    int deleteByPrimaryKey(userRoleKey key);

    int insert(userRoleKey record);

    int insertSelective(userRoleKey record);
}