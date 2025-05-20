package cn.mu00.tools.bookmark.domain;

import cn.mu00.tools.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("bookmark") // 对应数据库表名称
public class Bookmark extends BaseEntity {


    private static final long serialVersionUID = 1L;

    private String title;

    private String url;

    /**
     * default auto
     */
    private String icon;

    private String description;

    private String tags;

    private String group;

    // status 0 正常 1 高级
}
