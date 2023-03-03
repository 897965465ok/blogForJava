package com.jiang.blog.model.VO;

import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.model.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAndMenuVO implements Serializable {
    Role role;
    Menu[] resource;
}
