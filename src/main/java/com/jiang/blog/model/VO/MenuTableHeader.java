package com.jiang.blog.model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTableHeader  implements Serializable {
    private String menuId = "菜单编号";

    /**
     * 菜单名称
     */

    private String menuName ="菜单名称";

    /**
     * 权限标识
     */

    private String perms = "权限标识";

    /**
     * 菜单状态（0正常 1停用）
     */

    private String status ="菜单状态";

    /**
     * 创建时间
     */

    private String createTime = "创建时间";

    /**
     * 显示顺序
     */

    private String orderNum = "显示顺序";

}
