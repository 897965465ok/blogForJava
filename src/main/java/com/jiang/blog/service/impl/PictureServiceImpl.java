package com.jiang.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.model.dao.PictureUrlMapper;
import com.jiang.blog.model.pojo.PictureUrl;
import com.jiang.blog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl  extends ServiceImpl<PictureUrlMapper,PictureUrl>  implements PictureService {
    @Autowired
    PictureUrlMapper pictureUrlMapper;

    @Override
    @CachePut(value = "queryManyPicture")
    public PageInfo queryManyPicture(Integer offset, Integer limit) {
        // DESC表示降序
        PageHelper.startPage(offset, limit, "id");
        List<PictureUrl> pictureUrlList = pictureUrlMapper.queryManyPicture(offset, limit);
        PageInfo pageinfo = new PageInfo<>(pictureUrlList);

        return pageinfo;
    }
}
