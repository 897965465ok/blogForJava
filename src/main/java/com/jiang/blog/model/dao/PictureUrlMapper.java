package com.jiang.blog.model.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.pojo.PictureUrl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureUrlMapper extends BaseMapper<PictureUrl> {
    int deleteByPrimaryKey(Integer id);

    int insert(PictureUrl record);

    int insertSelective(PictureUrl record);

    PictureUrl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PictureUrl record);

    int updateByPrimaryKey(PictureUrl record);

    @Select(" select * from picture_url ")
    List<PictureUrl> queryManyPicture(Integer offset, Integer limit);
}