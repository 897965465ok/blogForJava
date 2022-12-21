package com.jiang.blog.model.dao.ExtendDao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiang.blog.model.dao.PictureUrlMapper;
import com.jiang.blog.model.pojo.PictureUrl;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendPictureUrlMapper extends BaseMapper<PictureUrl>, PictureUrlMapper {

    @Select(" select * from picture_url ")
    List<PictureUrl> queryManyPicture(Integer offset, Integer limit);
}