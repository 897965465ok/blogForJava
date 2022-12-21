package com.jiang.blog.model.dao.ExtendDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.dao.TagsMapper;
import com.jiang.blog.model.pojo.Tags;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendTagsMapper extends TagsMapper, BaseMapper<Tags> {

    List<Tags> queryByTagsAll();

    int tagsExists(@Param("article_tag") String articleTag);
}