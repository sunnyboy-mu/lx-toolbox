package cn.mu00.tools.common.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId // 使用 MyBatis-Plus 的注解标识为主键
    private String id; // 雪花算法生成的 ID

    private Integer status; // 0: 启用, 1: 禁用

    @TableLogic
    private Integer isDeleted; // 0: 正常, 1: 已删除

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss") // 使用 FastJSON 进行日期格式化
    private Date createdAt; // 创建时间

    private String createdBy; // 创建人

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss") // 使用 FastJSON 进行日期格式化
    private Date updatedAt; // 更新时间

    private String updatedBy; // 更新人
}
