package com.jiang.blog.model.VO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTableHeader {
    // id
    private String userId = "编号";
    // 账号
    private String userName = "账号";
    // 昵称
    private String nickName = "名称";
    // 类型
    private String userType = "类型";
    // 邮箱
    private String email = "邮箱";
    // 手机号码
    private String phonenumber = "手机号码";
    // 性别
    private String sex = "性别";
    // 头像
    private String avatar = "头像";
    // 密码
    private String password = "密码";
    // 状态
    private String status = "状态";
    // 创建世间
    private String createTime = "创建时间";
}
