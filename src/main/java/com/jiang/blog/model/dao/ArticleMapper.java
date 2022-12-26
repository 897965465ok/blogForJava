package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.Article;import org.apache.ibatis.annotations.Param;import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> queryArticlesByTags(@Param("tags") String tags);

    List<Article> queryArticles(@Param("offset") Integer offset, @Param("limit") Integer limit);
}