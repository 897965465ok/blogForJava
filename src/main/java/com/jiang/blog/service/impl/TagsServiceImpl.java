package com.jiang.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.blog.exception.BlogException;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.dao.TagsMapper;
import com.jiang.blog.model.pojo.Tags;
import com.jiang.blog.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagsServiceImpl  extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    @Autowired
    TagsMapper tagsMapper;

    @Override
    @CachePut(value = "queryByTags")
    public List<Tags> queryByTags() {
        List<Tags> tagsList;
        tagsList = tagsMapper.queryByTagsAll();
        return tagsList;
    }

    @Override
    public int creatByTags(String articleTag) {

        Integer exists = tagsMapper.tagsExists(articleTag);

        if (exists != 1) {
            Tags tags = new Tags();
            tags.setArticleTag(articleTag);
            tags.setCreatedAt(new Date());
            tags.setUpdatedAt(new Date());
            return tagsMapper.insert(tags);
        } else {
            return 0;
        }
    }

    @Override
    public boolean deleteTags(Integer id) {
        return (tagsMapper.deleteById(id) == 1) ? true : false;
    }

    @Override
    public Integer updateTags(Integer id, String content) {
        Tags tags = tagsMapper.selectById(id);

        if (tags == null) {
            throw new BlogException(BlogExceptionEnum.TAGS_NOT_EXISTS);
        } else {
            tags.setUpdatedAt(new Date());
            tags.setArticleTag(content);
            return tagsMapper.updateById(tags);
        }
    }

}
