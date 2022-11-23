package com.jiang.blog.model.dao;

import com.jiang.blog.model.pojo.PictureUrl;

public interface PictureUrlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PictureUrl record);

    int insertSelective(PictureUrl record);

    PictureUrl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PictureUrl record);

    int updateByPrimaryKey(PictureUrl record);
}