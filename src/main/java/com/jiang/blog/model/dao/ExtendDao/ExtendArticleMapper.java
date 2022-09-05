package com.jiang.blog.model.dao.ExtendDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.dao.ArticleMapper;
import com.jiang.blog.model.pojo.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendArticleMapper extends BaseMapper<Article>, ArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> queryArticlesByTags(@Param("tags") String tags);

    List<Article> queryArticles(@Param("offset") Integer offset, @Param("limit") Integer limit);


}