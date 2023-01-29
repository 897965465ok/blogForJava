package com.jiang.blog.model.VO;

import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    PageInfo<User> pageInfo;
    UserTableHeader userTableHeader = new UserTableHeader();
}
