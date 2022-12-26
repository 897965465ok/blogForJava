package com.jiang.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.dao.UserRoleMapper;
import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public PageInfo queryManyMenu(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit, "menu_id");

        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>());

        PageInfo pageinfo = new PageInfo<>(menus);

        return pageinfo;
    }

    @Override
    public Menu queryOneMenu(Integer id) {
        return menuMapper.selectById(id);
    }

    @Override
    public Integer updateOneMenu(Menu menu) {
        return menuMapper.updateById(menu);
    }

    @Override
    public List getRouter() {
        // 根据用户生成路由器
     /*   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            Long userid = loginUser.getUser().getUserId();
        // 先查 用户ID查出角色(user_role) -》
        loginUser.getPermissions();*/

        // 先这用吧
        Menu menu =   menuMapper.selectById(1);
        QueryWrapper<Menu>   query =  new QueryWrapper();
        query.eq("parent_id",menu.getMenuId());
        Map<String, Object> menuMap = BeanUtil.beanToMap(menu);
        menuMap.put("children", menuMapper.selectList(query));
        List menuList =  new LinkedList<Map>();
        menuList.add(menuMap);
        return menuList;

    }



}
