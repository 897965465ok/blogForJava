package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.Menu;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userid );

}