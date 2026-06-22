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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    MinioServiceClient minioServiceClient;


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
    @Transactional
    public Integer deleteOneArticle(Integer id) {
        Article article = articleMapper.selectById(id.longValue());
        if (article != null && article.getName() != null) {
            minioServiceClient.removeObject(article.getName());
        }
        return articleMapper.deleteById(id.longValue());
    }

    @Override
    @Transactional
    public boolean deleteManyArticle(ArrayList<Long> ids) {
        // 先查出所有文章，获取文件名
        List<Article> articles = articleMapper.selectBatchIds(ids);
        for (Article article : articles) {
            if (article != null && article.getName() != null) {
                try {
                    minioServiceClient.removeObject(article.getName());
                } catch (Exception e) {
                    log.warn("删除 Minio 文件失败: {}", article.getName(), e);
                }
            }
        }
        return this.removeByIds(ids);
    }

    @Override
    public Article queryOneArticle(Long id) {
        return articleMapper.selectById(id.longValue());
    }


    @Override
    @Transactional
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
    @Transactional
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
    @Cacheable(value = "ArticleTableHeader")
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
        target.setCreatedAt(new Date());
        target.setUpdatedAt(new Date());
        this.save(target);
        return fileUrl;

    }

    @Override
    public String updateOneArticle(Article article, MultipartFile file) {
        // 从 Minio 删除旧文件
        minioServiceClient.removeObject(article.getName());
        // 上传新内容到 Minio
        String fileUrl = minioServiceClient.putObject(file);
        article.setArticlePath(fileUrl);
        article.setName(file.getOriginalFilename());
        article.setUpdatedAt(new Date());
        this.updateById(article);
        return "成功";
    }

    @Override
    @Transactional
    public Integer batchAddArticles(List<MultipartFile> files) {
        int count = 0;
        for (MultipartFile file : files) {
            try {
                String originalName = file.getOriginalFilename();
                if (originalName == null || !originalName.endsWith(".md")) continue;

                // 从路径中提取文件夹名作为 tag
                String tag = "";
                String fileName = originalName;
                if (originalName.contains("/")) {
                    String[] parts = originalName.split("/");
                    if (parts.length >= 2) {
                        tag = parts[0];
                        fileName = parts[parts.length - 1];
                    }
                }

                // 读取 markdown 内容
                StringBuilder content = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                }

                // 上传到 Minio
                String fileUrl = minioServiceClient.putObject(file);
                if (fileUrl == null) continue;

                // 创建文章记录
                Article article = new Article();
                article.setName(fileName);
                article.setArticlePath(fileUrl);
                article.setTag(tag);
                String title = fileName.replaceAll("\\.md$", "");
                article.setContent(content.toString());
                article.setCreatedAt(new Date());
                article.setUpdatedAt(new Date());
                this.save(article);
                count++;
            } catch (Exception e) {
                log.error("批量导入文章失败: {}", file.getOriginalFilename(), e);
            }
        }
        return count;
    }
}
