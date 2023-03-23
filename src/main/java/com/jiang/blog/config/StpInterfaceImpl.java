package com.jiang.blog.config;

import cn.dev33.satoken.stp.StpInterface;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.dao.UserMapper;
import com.jiang.blog.model.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String id = loginId.toString();
        List<String> permissionList = new LinkedList<>();

        if (id.equals(1)) {
            permissionList.add("*");
        } else {
        // 如果不是管理员
            List<String> Permission = menuMapper.selectPermsByUserId(Long.parseLong(id));
            permissionList.addAll(Permission);
        }
        return permissionList;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String id = loginId.toString();
        List<String> roleName = new LinkedList<>();
        if (id.equals(1)) {
            roleName.add("admin");
        } else {
            List<Role> roles = userMapper.queryRolesByUserId(Long.parseLong(id));
           roleName.addAll(  roles.stream().map(item->item.getRoleKey()).collect(Collectors.toList()));

        }
        return roleName;
    }

}
