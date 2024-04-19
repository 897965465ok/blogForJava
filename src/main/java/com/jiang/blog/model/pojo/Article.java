package com.jiang.blog.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "article")
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
    @TableField(value = "deleted_at")
    private Date deletedAt;
    @TableField(value = "picture")
    private String picture;
    @TableField(value = "paragraph")
    private String paragraph;
    @TableField(value = "`name`")
    private String name;
    @TableField(value = "tag")
    private String tag;
    @TableField(value = "article_path")
    private String articlePath;
    @TableField(value = "whatch_number")
    private Integer whatchNumber;
    @TableField(value = "`like`")
    private Integer like;
    @TableField(value = "cover")
    private String cover;
    @TableField(value = "content")
    private String content;
    @TableField(value = "hot")
    private Long hot;
    @TableField(value = "rec")
    private Long rec;
    @TableField(value = "comments_count")
    private Long commentsCount;
    @TableField(value = "side_article")
    private Long sideArticle;
}