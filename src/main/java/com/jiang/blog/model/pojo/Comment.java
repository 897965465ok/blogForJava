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
@TableName(value = "`comment`")
public class Comment implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;

    @TableField(value = "deleted_at")
    private Date deletedAt;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "article_id")
    private Long articleId;

    @TableField(value = "manager_id")
    private Long managerId;

    @TableField(value = "content")
    private String content;

    @TableField(value = "is_reply")
    private Byte isReply;

    @TableField(value = "reply_id")
    private Long replyId;

    @TableField(value = "`to`")
    private String to;

    private static final long serialVersionUID = 1L;
}