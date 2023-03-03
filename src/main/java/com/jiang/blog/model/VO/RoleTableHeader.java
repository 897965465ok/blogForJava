package com.jiang.blog.model.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleTableHeader implements Serializable {

    /**
     * 角色ID
     */

    private String roleId ="角色编号";

    /**
     * 角色名称
     */

    private String roleName ="角色名称";

    /**
     * 角色权限字符串
     */

    private String roleKey = "角色权限标识";

    /**
     * 显示顺序
     */

    private String roleSort =" 显示顺序";

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
//    @TableField(value = "data_scope")
//    private String dataScope;

    /**
     * 菜单树选择项是否关联显示
     */
//    @TableField(value = "menu_check_strictly")
//    private Byte menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示
     */
//    @TableField(value = "dept_check_strictly")
//    private Byte deptCheckStrictly;

    /**
     * 角色状态（0正常 1停用）
     */

    private String status = "角色状态";

    /**
     * 删除标志（0代表存在 2代表删除）
     */

    private String delFlag = "删除标志";

    /**
     * 创建者
     */

    private String createBy ="创建者";

    /**
     * 创建时间
     */
//    @TableField(value = "create_time")
//    private Date createTime;

    /**
     * 更新者
     */
//    @TableField(value = "update_by")
//    private String updateBy;

    /**
     * 更新时间
     */
//    @TableField(value = "update_time")
//    private Date updateTime;

    /**
     * 备注
     */
//    @TableField(value = "remark")
    private String remark = "备注";

}
