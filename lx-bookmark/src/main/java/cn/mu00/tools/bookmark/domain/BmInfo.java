package cn.mu00.tools.bookmark.domain;

import cn.mu00.tools.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("bm_info") // 对应数据库表名称
public class BmInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String title;

    private String url;

    /**
     * default auto
     */
    private String icon;

    private String description;

    @TableField("group_id")
    private String groupId;
}
