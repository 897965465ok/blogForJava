package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.VO.ArticleAndFileVO;
import com.jiang.blog.model.VO.ArticleTableHeader;
import com.jiang.blog.model.pojo.Article;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ArticleService  extends IService<Article> {
    PageInfo queryArticlesByTags(String tags, Integer offset, Integer limit);

    PageInfo queryManyArticle(Integer offset, Integer limit);

    Integer deleteOneArticle(Integer id);

    boolean deleteManyArticle(ArrayList<Long> id);

    Article queryOneArticle(Long id);

    void favor(Integer id);

    void visit(Integer id);


    ArticleTableHeader ArticleTableHeader();




    String addOneArticle(ArticleAndFileVO articleAndFileVO) throws IOException;


    String updateOneArticle(Article article, MultipartFile file);

    Integer batchAddArticles(List<MultipartFile> files) throws IOException;
}
