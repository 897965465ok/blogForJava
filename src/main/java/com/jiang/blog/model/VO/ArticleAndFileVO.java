package com.jiang.blog.model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAndFileVO {

  @Nullable
  private String name  ;
  private String tag ;
  private String hot ;
  private String sideArticle ;
  @Nullable
  private String paragraph;
  @Nullable
  private String picture;
  @Nullable
  private String cover;
  private Integer whatchNumber = 0;
  private Integer like = 0;
  private Long rec = 0L;
  public MultipartFile file;
}
