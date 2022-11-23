package com.jiang.blog.model.dao.ExtendDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendMenuMapper extends BaseMapper<Menu>, MenuMapper {

    List<String> selectPermsByUserId(Long userId);
}
