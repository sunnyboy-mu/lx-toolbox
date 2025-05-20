package cn.mu00.tools.blog.domain;

import cn.mu00.tools.common.domain.BaseEntity;
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

    private Integer top;

    /**
     * NOTE 笔记
     * POST 文章
     */
    private String type;

    /**
     * status
     * 0 正常
     * 1 高级
     * 2 回收站
     */

}
