package com.jiang.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.VO.ArticleAndFileVO;
import com.jiang.blog.model.VO.ArticleTableHeader;
import com.jiang.blog.model.dao.ArticleMapper;
import com.jiang.blog.model.pojo.Article;
import com.jiang.blog.service.ArticleService;
import com.jiang.blog.utils.MinioServiceClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    MinioServiceClient minioServiceClient;


    @Override
    // 根据标签查询
    @CachePut(value = "queryArticlesByTags")
    public PageInfo queryArticlesByTags(String tags, Integer offset, Integer limit) {
        PageHelper.startPage(offset, limit);
        List<Article> articles = articleMapper.queryArticlesByTags(tags);
        PageInfo pageinfo = new PageInfo<>(articles);
        return pageinfo;
    }

    // 查询文章
    @Override
    @CachePut(value = "queryManyArticle")
    public PageInfo queryManyArticle(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit, "id");
        List<Article> articles = articleMapper.queryArticles(offset, limit);
        PageInfo pageinfo = new PageInfo<>(articles);

        return pageinfo;
    }

    @Override
    public Integer deleteOneArticle(Integer id) {
        return articleMapper.deleteById(id.longValue());
    }

    @Override
    public boolean deleteManyArticle(ArrayList<Long> id) {
        return this.removeByIds(id);
    }

    @Override
    public Article queryOneArticle(Long id) {
        return articleMapper.selectById(id.longValue());
    }


    @Override
    public void favor(Integer id) {
        Article article = articleMapper.selectById(id.longValue());
        if (null == article) {
            throw new BlogException(BlogExceptionEnum.ARTICLE_NOT_EXISTS);
        } else {
            article.setLike(article.getLike() + 1);
            article.setUpdatedAt(new Date());
            articleMapper.updateById(article);
        }
    }

    @Override
    public void visit(Integer id) {
        Article article = articleMapper.selectById(id.longValue());
        if (null == article) {
            throw new BlogException(BlogExceptionEnum.ARTICLE_NOT_EXISTS);
        } else {
            article.setWhatchNumber(article.getWhatchNumber() + 1);
            article.setUpdatedAt(new Date());
            articleMapper.updateById(article);
        }
    }

    @Override
    @CachePut(value = "ArticleTableHeader")
    public ArticleTableHeader ArticleTableHeader() {
        return new ArticleTableHeader();
    }

    @Override
    public String addOneArticle(ArticleAndFileVO articleAndFileVO) {
        Article target = new Article();
        BeanUtils.copyProperties(articleAndFileVO, target);
        MultipartFile file = articleAndFileVO.getFile();
        String fileUrl = minioServiceClient.putObject(file);
        target.setName(file.getOriginalFilename());
        target.setArticlePath(fileUrl);
        this.saveOrUpdate(target);
        return fileUrl;

    }

    @Override
    public String updateOneArticle(Article article, MultipartFile file) {

            minioServiceClient.removeObject(article.getName());
            String fileUrl = minioServiceClient.putObject(file);
            article.setArticlePath(fileUrl);
            this.saveOrUpdate(article);

        return "成功";
    }

}
