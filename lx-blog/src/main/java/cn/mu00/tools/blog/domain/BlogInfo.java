package cn.mu00.tools.blog.domain;

import cn.mu00.tools.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("blog_info")
public class BlogInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String title;

    private String description;

    private String icon;

    private String banner;

    private String sourceText;

    private String renderText;

    private String catalog;

    private String tags;

    private Integer sort;

    private Integer special;

    private Integer top;

    private String groupId;

    private String categoryId;

    @TableField(exist = false)
    private Boolean publish;

    /**
     * status
     * 0 未发布
     * 1 已发布
     * 2 回收站
     */

}
