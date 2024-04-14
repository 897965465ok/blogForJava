package com.jiang.blog.model.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsTableHeader  implements Serializable {

    private String id = "编号";

    private String createdAt = "创建时间";


    private String updatedAt = "修改时间";


    private String deletedAt = "删除时间";

    private String articleTag = "标签名";

}
