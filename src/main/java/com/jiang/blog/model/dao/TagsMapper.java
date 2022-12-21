package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.Tags;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagsMapper extends BaseMapper<Tags> {

    List<Tags> queryByTagsAll();

    int tagsExists(@Param("article_tag") String articleTag);

    
}