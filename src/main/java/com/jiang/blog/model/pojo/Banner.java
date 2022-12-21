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
@TableName(value = "banner")
public class Banner implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;

    @TableField(value = "deleted_at")
    private Date deletedAt;

    @TableField(value = "uuid")
    private String uuid;

    @TableField(value = "url")
    private String url;

    @TableField(value = "short_url")
    private String shortUrl;

    @TableField(value = "`path`")
    private String path;

    @TableField(value = "`large`")
    private String large;

    @TableField(value = "original")
    private String original;

    @TableField(value = "small")
    private String small;

    @TableField(value = "img_url_id")
    private String imgUrlId;

    private static final long serialVersionUID = 1L;
}