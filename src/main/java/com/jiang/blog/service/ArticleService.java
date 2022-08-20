package com.jiang.blog.service;

import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.pojo.Article;

public interface ArticleService {
    PageInfo queryArticlesByTags(String tags, Integer offset, Integer limit);

    PageInfo queryManyArticle(Integer offset, Integer limit);

    Integer deleteOneArticle(Integer id);

    Article queryOneArticle(Integer id);

    void favor(Integer id);

    void visit(Integer id);
}
