package cn.mu00.tools.blog.domain;

import cn.mu00.tools.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("blog_catalog")
public class BlogCatalog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;

    private String icon;

    private Integer sort;

    private String type;

    private String url;

    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String parentId;

    @TableField(exist = false)
    private List<BlogCatalog> children;

}
