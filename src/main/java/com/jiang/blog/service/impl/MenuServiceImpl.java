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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MenuServiceImpl  extends ServiceImpl<MenuMapper,Menu> implements MenuService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    @Cacheable(value = "queryManyMenu")
    public PageInfo queryManyMenu(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit, "menu_id");

        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>());

        PageInfo pageinfo = new PageInfo<>(menus);

        return pageinfo;
    }

    @Override
    @Cacheable(value = "queryMenuTableHeader")
    public MenuTableHeader queryMenuTableHeader() {
        return   new MenuTableHeader();
    }




    @Override
    @Cacheable(value = "queryOneMenu")
    public Menu queryOneMenu(Integer id) {
        return menuMapper.selectById(id);
    }

    @Override
    public Integer updateOneMenu(Menu menu) {
        return menuMapper.updateById(menu);
    }

    @Override
    @Cacheable(value = "getRouter")
    public List getRouter() {
        // 根据用户生成路由器
     /*   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            Long userid = loginUser.getUser().getUserId();
        // 先查 用户ID查出角色(user_role) -》
        loginUser.getPermissions();*/
        LambdaQueryWrapper<Menu> query =  new LambdaQueryWrapper();
        query.eq(Menu::getParentId,0);
        List<Menu> menus =   this.list(query);
        LinkedList<Map>  treeMenus = new LinkedList<>();
        for (Menu menu: menus) {
           Map<String,Object>  result =   generatorTreeMenu(menu);
           treeMenus.add(result);
        }

        return  treeMenus;
    }

      private   Map generatorTreeMenu(Menu menu){
        LambdaQueryWrapper<Menu> query =  new LambdaQueryWrapper();
        query.eq(Menu::getParentId,menu.getMenuId());
        Map<String, Object> menuMap = BeanUtil.beanToMap(menu);
        menuMap.put("children", menuMapper.selectList(query));
        return  menuMap;
    }


    @Override
    public  boolean deleteMenuByOne(String id){
        return  this.removeById(id);
    }






}
