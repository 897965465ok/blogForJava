package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.VO.ArticleAndFileVO;
import com.jiang.blog.model.VO.ArticleTableHeader;
import com.jiang.blog.model.pojo.Article;

import java.io.IOException;

public interface ArticleService  extends IService<Article> {
    PageInfo queryArticlesByTags(String tags, Integer offset, Integer limit);

    PageInfo queryManyArticle(Integer offset, Integer limit);

    Integer deleteOneArticle(Integer id);

    Article queryOneArticle(Integer id);

    void favor(Integer id);

    void visit(Integer id);


    ArticleTableHeader ArticleTableHeader();




    boolean addOneArticle(ArticleAndFileVO articleAndFileVO) throws IOException;
}
