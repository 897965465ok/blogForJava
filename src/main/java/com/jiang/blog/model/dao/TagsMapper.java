package com.jiang.blog.model.dao;

import com.jiang.blog.model.pojo.Tags;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tags record);

    int insertSelective(Tags record);

    Tags selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);

}