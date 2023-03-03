package com.jiang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.VO.RoleTableHeader;
import com.jiang.blog.model.dao.RoleMapper;
import com.jiang.blog.model.dao.RoleMenuMapper;
import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.model.pojo.RoleMenu;
import com.jiang.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//Iserver配置
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public PageInfo queryManyRole(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit);

        QueryWrapper<Role> query = new QueryWrapper();

        List<Role> roles = roleMapper.selectList(query);

        PageInfo pageinfo = new PageInfo<Role>(roles);

        return pageinfo;
    }


    @Override
    public RoleTableHeader queryRoleTableHeader() {
        return new RoleTableHeader();
    }

    @Override
    public int createRole(Role role, Menu[] menus) {
        int result = roleMapper.insert(role);
        if (result == 1) {
            Arrays.stream(menus).forEach((menu -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menu.getMenuId());
                roleMenu.setRoleId(role.getRoleId());
                roleMenuMapper.insert(roleMenu);
            }));
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Long deleteManyRole(ArrayList<Role> roles) {
        LambdaQueryWrapper<RoleMenu> query = new LambdaQueryWrapper();
        List<Long> ids = roles.
                stream().
                map(role -> role.getRoleId()).
                collect(Collectors.toList());
        long count = ids.
                stream().
                map((Long id) -> {
                    query.eq(RoleMenu::getRoleId, id);
                    return roleMenuMapper.delete(query);
                }).count();
        return count + roleMapper.deleteBatchIds(ids);
    }

}
