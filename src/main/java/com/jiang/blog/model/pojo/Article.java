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
@TableName(value = "article")
public class Article implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private Date createdAt;

    /**
     * 上传时间
     */
    @TableField(value = "updated_at")
    private Date updatedAt;

    /**
     * 删除时间
     */
    @TableField(value = "deleted_at")
    private Date deletedAt;

    /**
     * 图片链接
     */
    @TableField(value = "picture")
    private String picture;

    /**
     * 文章段落
     */
    @TableField(value = "paragraph")
    private String paragraph;

    /**
     * 名字
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 分类
     */
    @TableField(value = "tag")
    private String tag;

    /**
     * 原文件路径
     */
    @TableField(value = "article_path")
    private String articlePath;

    /**
     * 查看次数
     */
    @TableField(value = "whatch_number")
    private Integer whatchNumber;

    /**
     * 喜欢
     */
    @TableField(value = "`like`")
    private Integer like;

    /**
     * 封面
     */
    @TableField(value = "cover")
    private String cover;

    /**
     * 解析后的内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 是否热门
     */
    @TableField(value = "hot")
    private Long hot;

    /**
     * 推荐
     */
    @TableField(value = "rec")
    private Long rec;

    /**
     * 评论次数
     */
    @TableField(value = "comments_count")
    private Long commentsCount;

    /**
     * 侧边栏
     */
    @TableField(value = "side_article")
    private Long sideArticle;

    private static final long serialVersionUID = 1L;
}