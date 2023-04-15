package com.jiang.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.VO.MenuTableHeader;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.dao.UserRoleMapper;
import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserRoleMapper userRoleMapper;


    @Override
    public boolean deleteManyMenu(List<Menu> Menus) {
        List<Long> ids = Menus.stream().
                map((Menu menu) -> menu.getMenuId()).
                collect(Collectors.toList());
        return this.removeByIds(ids);
    }


    @Override
    @CachePut(value = "queryManyMenu")
    public PageInfo queryManyMenu(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit, "menu_id");

        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>());

        PageInfo pageinfo = new PageInfo<>(menus);

        return pageinfo;
    }

    @Override
    @CachePut(value = "queryMenuTableHeader")
    public MenuTableHeader queryMenuTableHeader() {
        return new MenuTableHeader();
    }


    @Override
    @CachePut(value = "queryOneMenu")
    public Menu queryOneMenu(Integer id) {
        return menuMapper.selectById(id);
    }

    @Override
    public Integer updateOneMenu(Menu menu) {
        return menuMapper.updateById(menu);
    }

    @Override
    @CachePut(value = "getRouter")
    public List getRouter() {
        // 根据用户生成路由器
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper();
        query.eq(Menu::getMenuType, "M");
        List<Menu> menus = menuMapper.selectList(query);
        return generatorTreeMenu(menus);
    }

    private List<Map> generatorTreeMenu(List<Menu> menus) {
        return menus.stream().map((Menu menu) -> {
            LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper();
            query.eq(Menu::getParentId, menu.getMenuId());
            List<Map> children = generatorTreeMenu(menuMapper.selectList(query));
            Map<String, Object> complete = BeanUtil.beanToMap(menu);
            complete.put("children", children);
            return complete;
        }).collect(Collectors.toList());
    }


    @Override
    public boolean deleteMenuByOne(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean createMenu(Menu menu) {
        return this.saveOrUpdate(menu);
    }


}
