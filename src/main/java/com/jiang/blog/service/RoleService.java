package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.VO.RoleTableHeader;
import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.model.pojo.Role;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;


// 使用Iserver接口
public interface RoleService extends IService<Role> {


    @Cacheable(value = "getPermsByRoleId") // 修改时用这个
    List getPermsByRoleId(Role role);

    PageInfo queryManyRole(Integer offset, Integer limit);

    RoleTableHeader queryRoleTableHeader();

    int  createRole(Role role, Menu[] menus);

    int changeRole(Role role, Menu[] menus);

    Long deleteManyRole(ArrayList<Role> roles);
}
