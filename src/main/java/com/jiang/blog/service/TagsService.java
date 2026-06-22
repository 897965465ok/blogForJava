package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.blog.model.VO.TagsTableHeader;
import com.jiang.blog.model.pojo.Tags;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;

public interface TagsService extends IService<Tags> {
    List<Tags> queryByTags();

    int creatByTags(String articleTag);

    boolean deleteTags(ArrayList<Long> id);

    Long updateTags(Long id, String content);

    @Cacheable(value = "TagsTableHeader")
    TagsTableHeader queryTagsTableHeader();
}
