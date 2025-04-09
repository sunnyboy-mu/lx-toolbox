package cn.mu00.tools.blog.domain;

import cn.mu00.tools.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("blog_category")
public class BlogCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;

    private String icon;

    private Integer sort;

}
