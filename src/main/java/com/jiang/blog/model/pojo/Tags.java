package com.jiang.blog.model.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tags")
public class Tags implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "created_at")
    private Date createdAt;
    @TableField(value = "updated_at")
    private Date updatedAt;
    @TableField(value = "deleted_at")
    private Date deletedAt;
    @TableField(value = "article_tag")
    private String articleTag;
}