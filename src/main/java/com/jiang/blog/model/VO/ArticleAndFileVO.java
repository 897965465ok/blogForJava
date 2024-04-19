package com.jiang.blog.model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAndFileVO  {

  @Nullable
  private String name  ;
  private String tag ;
  private String hot ;
  private String sideArticle ;
  public MultipartFile file;
}
