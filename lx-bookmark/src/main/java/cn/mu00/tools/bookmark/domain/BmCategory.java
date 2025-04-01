package cn.mu00.tools.bookmark.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@TableName("bm_category") // 对应数据库表名称
public class BmCategory implements Serializable {

    private static final long serialVersionUID = 1L; // 添加 serialVersionUID

    @TableId // 主键 ID，使用 MyBatis-Plus 注解
    private String id;  // 雪花算法生成的 ID

    private String name; // 分类名称

    private String icon; // 菜单图标

    private Integer sort; // 排序

}
