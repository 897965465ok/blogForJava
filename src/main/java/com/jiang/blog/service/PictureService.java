package com.jiang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.pojo.Article;
import com.jiang.blog.model.pojo.PictureUrl;

public interface PictureService extends IService<PictureUrl> {

    PageInfo queryManyPicture(Integer offset, Integer limit);
}
