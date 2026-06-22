package com.jiang.blog.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "picture_url")
public class PictureUrl implements Serializable {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "is_banner")
    private Integer isBanner;

    @TableField(value = "created_at")
    private Date createdAt;

    @TableField(value = "updated_at")
    private Date updatedAt;

    @TableField(value = "deleted_at")
    private Date deletedAt;

    @TableField(value = "url")
    private String url;

    @TableField(value = "short_url")
    private String shortUrl;

    @TableField(value = "category")
    private String category;

    @TableField(value = "file_type")
    private String fileType;

    @TableField(value = "`path`")
    private String path;

    @TableField(value = "`large`")
    private String large;

    @TableField(value = "original")
    private String original;

    @TableField(value = "small")
    private String small;

    @TableField(value = "dimension_x")
    private Long dimensionX;

    @TableField(value = "dimension_y")
    private Long dimensionY;

    @TableField(value = "file_size")
    private Long fileSize;

    private static final long serialVersionUID = 1L;
}