package com.jiang.blog.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.blog.model.VO.UserAndRolesIdVO;
import com.jiang.blog.model.VO.UserInfoVO;
import com.jiang.blog.model.VO.UserTableHeader;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.model.pojo.User;
import org.springframework.cache.annotation.CachePut;

import java.util.ArrayList;
import java.util.List;

public interface UserService  extends IService<User> {


    int userUpdate(User user);

    Long register(UserAndRolesIdVO userAndRolesIdVO);

    SaTokenInfo userLogin(String account, String password);

    @CachePut(value = "queryPermissionByUserId")
    List<Role> queryPermissionByUserId(User user);

    UserTableHeader queryUserTableHeader();

    List queryManyUser(Integer offset, Integer limit);

    int deleteManyUser(ArrayList<String> ids);

    User selectByUserName(String account);

    UserInfoVO getInfo();

    List<Role> queryRolesByUserId(User user);
}
