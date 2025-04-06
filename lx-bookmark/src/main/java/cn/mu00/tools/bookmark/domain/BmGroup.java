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
@TableName("bm_group") // 对应数据库表名称
public class BmGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组名称
     */
    private String title;

    @TableField("category_id")
    private String categoryId;

    private Integer sort; // 排序
}
