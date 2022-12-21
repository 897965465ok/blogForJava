package com.jiang.blog.model.dao.ExtendDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.dao.UserMapper;
import com.jiang.blog.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtendUserMapper extends BaseMapper<User>, UserMapper {

    Integer userExists(@Param("userName") String account);

    User selectByUserName(String account);

}