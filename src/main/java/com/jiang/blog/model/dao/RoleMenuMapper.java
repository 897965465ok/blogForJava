package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.model.pojo.RoleMenu;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Menu> getPermsByRoleId(long id);
}