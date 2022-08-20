package com.jiang.blog.service;

import com.github.pagehelper.PageInfo;

public interface PictureService {


    PageInfo queryManyPicture(Integer offset, Integer limit);
}
