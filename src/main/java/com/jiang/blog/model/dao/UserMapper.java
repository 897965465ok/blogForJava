package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.model.pojo.User;import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    Integer userExists(@Param("userName") String account);

    User selectByUserName(String account);

    List<User> queryManyUser();

   // TODO 写完sql语句
    List<Role> queryRolesByUserId(Long account);
}