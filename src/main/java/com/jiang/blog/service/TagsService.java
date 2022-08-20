package com.jiang.blog.service;

import com.jiang.blog.model.pojo.Tags;

import java.util.List;

public interface TagsService {
    List<Tags> queryByTags();

    int creatByTags(String articleTag);

    boolean deleteTags(Integer id);

    Integer updateTags(Integer id, String content);
}
