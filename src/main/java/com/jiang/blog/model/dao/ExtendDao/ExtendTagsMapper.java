package com.jiang.blog.model.dao.ExtendDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.dao.TagsMapper;
import com.jiang.blog.model.pojo.Tags;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendTagsMapper extends TagsMapper, BaseMapper<Tags> {
    int deleteByPrimaryKey(Integer id);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);

    List<Tags> queryByTagsAll();

    int tagsExists(@Param("article_tag") String articleTag);
}