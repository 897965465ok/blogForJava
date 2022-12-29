package com.jiang.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.dao.RoleMapper;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl  implements RoleService  {


    @Autowired
    RoleMapper roleMapper;

    @Override
    public PageInfo queryManyRole(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit);

        QueryWrapper<Role> query = new QueryWrapper();

        List<Role> roles = roleMapper.selectList(query);

        PageInfo pageinfo = new PageInfo<Role>(roles);

        return pageinfo;
    }
}
