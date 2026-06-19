package com.jiang.blog.service.impl;

import cn.dev33.satoken.stp.StpUtil;
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

import java.util.List;
import java.util.Map;
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
        return new MenuTableHeader();
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

        List<Menu> origin = filterMenu();

        List<Menu> filterMenus = origin.
                stream().
                filter((Menu menu) -> menu.getMenuType().equals("M")).
                collect(Collectors.toList());

        List<Map> menus = generatorTree(filterMenus, origin);

        return menus;
    }


    private List<Map> generatorTree(List<Menu> menus, List<Menu> origin) {
        return menus.stream().map((Menu menu) -> {
            // 拿到子菜单
            List<Menu> builtMenu = origin.stream().
                    filter(item -> item.getParentId().
                            equals(menu.getMenuId())).
                    collect(Collectors.toList());
            // 拿到子菜单的子菜单
            List<Map> children = generatorTree(builtMenu, origin);
            Map<String, Object> complete = BeanUtil.beanToMap(menu);
            complete.put("children", children);
            return complete;
        }).collect(Collectors.toList());

    }

    private List<Menu> filterMenu() {
        List<Menu> menus;
        boolean containsAdmin = StpUtil.getRoleList()
                .stream()
                .anyMatch(s -> s.contains("admin"));
        if (containsAdmin) {
            LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper();
            menus = menuMapper.selectList(query);
        } else {
            // 根据用户生成路由器
            List<String> permissions = StpUtil.getPermissionList();
            List<String> perms = permissions.stream()
                    .filter(item -> item.contains("list"))
                    .map(permission -> permission.split(":")[0])
                    .collect(Collectors.toList());
            menus = menuMapper.getMenusByPerms(perms);
        }
        return menus;

    }


    @Override
    public boolean deleteMenuByOne(Long id) {
        return this.removeById(id);
    }

    @Override
    public boolean createMenu(Menu menu) {
        return this.saveOrUpdate(menu);
    }


}
