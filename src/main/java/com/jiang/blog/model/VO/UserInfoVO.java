package com.jiang.blog.model.VO;


import com.jiang.blog.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    public User user;
    public List<String> permissions ;
    public List<String> roles ;
}
