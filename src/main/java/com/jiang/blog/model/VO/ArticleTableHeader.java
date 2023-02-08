package com.jiang.blog.model.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTableHeader {
    /**
     * id
     */
    private String id = "文章编号";

    /**
     * 创建时间
     */
    private String createdAt = "创建时间";

    /**
     * 图片链接
     */
    private String picture = "图片链接";

    /**
     * 文章段落
     */

    private String paragraph = "文章段落";

    /**
     * 名字
     */

    private String name = "名字";

    /**
     * 分类
     */

    private String tag = "文章分类";

    /**
     * 原文件路径
     */
    private String articlePath = "原文件路径";

    /**
     * 查看次数
     */

    private String whatchNumber = "查看次数";

    /**
     * 喜欢
     */

    private String like = "赞";

    /**
     * 封面
     */

    private String cover = "封面链接";

    /**
     * 是否热门
     */

    private String hot = "热门";

    /**
     * 推荐
     */

    private String rec = "推荐";
    /**
     * 侧边栏
     */
    private String sideArticle = "侧边栏";


}
