package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.Banner;

import java.util.List;

public interface BannerMapper extends BaseMapper<Banner> {
    List<String> selectPermsByUserId(Long userId);
}