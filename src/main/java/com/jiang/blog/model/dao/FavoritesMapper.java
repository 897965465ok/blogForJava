package com.jiang.blog.model.dao;

import com.jiang.blog.model.pojo.Favorites;

public interface FavoritesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);
}