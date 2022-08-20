package com.jiang.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.dao.ArticleMapper;
import com.jiang.blog.model.pojo.Article;
import com.jiang.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    // 根据标签查询
    public PageInfo queryArticlesByTags(String tags, Integer offset, Integer limit) {
        PageHelper.startPage(offset, limit);
        List<Article> articles = articleMapper.queryArticlesByTags(tags);
        PageInfo pageinfo = new PageInfo<>(articles);
        return pageinfo;
    }

    // 查询文章
    @Override
    public PageInfo queryManyArticle(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit, "id");
        List<Article> articles = articleMapper.queryArticles(offset, limit);
        PageInfo pageinfo = new PageInfo<>(articles);

        return pageinfo;
    }

    @Override
    public Integer deleteOneArticle(Integer id) {
        return articleMapper.deleteByPrimaryKey(id.longValue());
    }

    @Override
    public Article queryOneArticle(Integer id) {
        return articleMapper.selectByPrimaryKey(id.longValue());
    }


    @Override
    public void favor(Integer id) {
        Article article = articleMapper.selectByPrimaryKey(id.longValue());
        if (null == article) {
            throw new BlogException(BlogExceptionEnum.ARTICLE_NOT_EXISTS);
        } else {
            article.setLike(article.getLike() + 1);
            article.setUpdatedAt(new Date());
            articleMapper.updateByPrimaryKey(article);
        }
    }

    @Override
    public void visit(Integer id) {
        Article article = articleMapper.selectByPrimaryKey(id.longValue());
        if (null == article) {
            throw new BlogException(BlogExceptionEnum.ARTICLE_NOT_EXISTS);
        } else {
            article.setWhatchNumber(article.getWhatchNumber() + 1);
            article.setUpdatedAt(new Date());
            articleMapper.updateByPrimaryKey(article);
        }
    }


}