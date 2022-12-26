package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.PictureUrl;import org.apache.ibatis.annotations.Select;import java.util.List;

public interface PictureUrlMapper extends BaseMapper<PictureUrl> {
    @Select(" select * from picture_url ")
    List<PictureUrl> queryManyPicture(Integer offset, Integer limit);
}