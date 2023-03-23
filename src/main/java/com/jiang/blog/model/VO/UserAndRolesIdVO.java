package com.jiang.blog.model.VO;


import com.jiang.blog.model.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndRolesIdVO {
   public User user;
   public List<String> rolesId;

}
